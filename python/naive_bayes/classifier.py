import numpy as np
import matplotlib.pyplot as plt
import pickle

from sklearn import feature_extraction
from sklearn.metrics import confusion_matrix
from sklearn import model_selection as ms
from sklearn import naive_bayes
from sklearn import preprocessing
from sklearn.metrics import confusion_matrix

from .csv_extract import Data


class NaiveBayesClassification:
    def __init__(self):
        pass

    def get_train_data(self):
        data = Data('website_classification.csv')
        self.text_df = data.prepare()
        self.vectorizer = feature_extraction.text.CountVectorizer()
        self.X = self.vectorizer.fit_transform(
            self.text_df['cleaned_website_text'].values)
        # print(self.X.shape)
        self.y = self.text_df['Category'].values
        print(f'categories: {self.text_df["Category"].unique()}')

    def save_model(self, path):
        pickle.dump(self.model_naive, open(path, 'wb'))
        pickle.dump(self.vectorizer, open(
            '../trained_model/vectorizer_'+path, 'wb'))

    def train(self):
        X_train, X_test, y_train, y_test = ms.train_test_split(
            self.X, self.y, test_size=0.2, random_state=42)
        self.model_naive = naive_bayes.MultinomialNB()
        self.model_naive.fit(X_train, y_train)
        # print(model_naive.score(X_train, y_train))
        # print(model_naive.score(X_test, y_test))
        # print(confusion_matrix(y_test, model_naive.predict(X_test)))

    # def load_model(self, path):
    #     self.vectorizer = pickle.load(
    #         open('../trained_model/vectorizer_'+path, 'rb'))
    #     self.model_naive = pickle.load(open(path, 'rb'))

    # def predict(self, text):
    #     m = self.vectorizer.transform([text])
    #     return self.model_naive.predict(m)[0]


class PrepareModel:
    def __init__(self, path):
        self.vectorizer = pickle.load(
            open('trained_model/vectorizer_'+path, 'rb'))
        self.model_naive = pickle.load(open('trained_model/'+path, 'rb'))

    def predict(self, text):
        m = self.vectorizer.transform([text])
        return self.model_naive.predict(m)[0]


if __name__ == '__main__':
    print('start training...')
    cl = NaiveBayesClassification()
    cl.get_train_data()
    cl.train()
    cl.save_model('../trained_data/finalized_model.sav')
    print('...done')


# example = 'Carolina Panthers interim coach Steve Wilks fired two of his assistant coaches Monday, less than 24 hours after the team’s embarrassing 42-21'
# example2 = 'From exploring the rainforest to horseback riding in the Ecuadorian “wild west,” this tiny country offers many unexpected experiences. '
