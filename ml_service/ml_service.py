from flask import Flask, request, jsonify
import numpy as np
from sklearn.linear_model import LogisticRegression

app = Flask(__name__)

def build_simple_model(seed=0):
    rng = np.random.RandomState(seed)
    ages = rng.randint(20,80,size=(1000,1)) / 80.0
    bmi = rng.normal(loc=25, scale=4, size=(1000,1)) / 50.0
    g = rng.normal(loc=100, scale=20, size=(1000,1)) / 200.0
    gender = rng.randint(0,2,size=(1000,1))
    X = np.hstack([ages, bmi, g, gender])
    scores = 2*ages + 2*g + 0.5*bmi + 0.1*gender
    y = (scores.ravel() + rng.normal(scale=0.5, size=1000)) > 2.0
    clf = LogisticRegression(max_iter=200)
    clf.fit(X, y.astype(int))
    return clf


diabetes_model = build_simple_model(seed=1)
heart_model = build_simple_model(seed=2)


def risk_from_prob(p):
    if p < 0.33:
        return "low"
    if p < 0.66:
        return "medium"
    return "high"

@app.route('/predict/diabetes', methods=['POST'])
def predict_diabetes():
    data = request.get_json() or request.form
    age = float(data.get('age',0))
    bmi = float(data.get('bmi',0))
    glucose = float(data.get('glucose',0))
    gender = data.get('gender','male')
    g = 1 if gender.lower().startswith('m') else 0
    X = np.array([[age/80.0, bmi/50.0, glucose/200.0, g]])
    p = diabetes_model.predict_proba(X)[0,1]
    r = risk_from_prob(p)
    return jsonify({"condition":"diabetes","risk":r,"score":float(p)})

@app.route('/predict/heart', methods=['POST'])
def predict_heart():
    data = request.get_json() or request.form
    age = float(data.get('age',0))
    bmi = float(data.get('bmi',0))
    cholesterol = float(data.get('cholesterol',0))
    gender = data.get('gender','male')
    g = 1 if gender.lower().startswith('m') else 0
    X = np.array([[age/80.0, bmi/50.0, cholesterol/300.0, g]])
    p = heart_model.predict_proba(X)[0,1]
    r = risk_from_prob(p)
    return jsonify({"condition":"heart","risk":r,"score":float(p)})

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
