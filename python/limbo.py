from flask import Flask, request
from naive_bayes.classifier import PrepareModel

model = PrepareModel('finalized_model.sav')

app = Flask(__name__)


@app.route('/', methods=['POST'])
def main():
    try:
        request_data = request.get_json()
    except:
        return {
            'ok': False,
            'result': '400 Bad Request'
        }

    print(request_data)
    if request_data.get('text') == '':
        return{
            "ok":False,
            "id":request_data.get(id),
            "result":"Empty file"
        }
    text = request_data.get('text')
    querry_id = request_data.get('id')


    if querry_id is None or text is None:
        return {
            'ok': False,
            'result': '400 Bad Request'
        }

    try:
        predict = model.predict(text)
    except:
        return {
            'ok': False,
            'result': '500 Internal Error'
        }

    return {'id': querry_id,'result': predict}


app.run(host = '127.0.0.1',port = 5000,debug=True)

# TODO: add queue for requests
