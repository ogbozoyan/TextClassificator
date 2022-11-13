import pandas as pd
from itertools import chain
import nltk
#nltk.download('stopwords', quiet=True)
#nltk.download('punkt', quiet=True)
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize


class Data:
      def __init__(self, path):
            self.text_df = pd.read_csv(path, delimiter=',')
            del self.text_df['id'], self.text_df['website_url']
            # print(f'data before:\n{self.text_df.head(3)}')

      def prepare(self):
            categories = list(self.text_df['Category'].unique())
            # print('categories:\n{categories}')

            cleaned_website_text = list(self.text_df['cleaned_website_text'].unique())
            # print(f'unique data:\n {self.text_df.shape}')


            unique_text_cat = {}

            for category in categories:
                  mask = self.text_df['Category'] == category
                  uniques = len(list(self.text_df[mask]['cleaned_website_text'].unique()))
                  unique_text_cat.update({category: uniques})

            # print(unique_text_cat)
            sorted_unique_text_cat = {}
            sorted_keys = sorted(unique_text_cat, key=unique_text_cat.get)

            for w in list(reversed(sorted_keys)):
                  sorted_unique_text_cat[w] = unique_text_cat[w]

            # prinst(sorted_unique_text_cat)

            self.text_df = self.text_df.drop_duplicates()
            # print(self.text_df.shape)

            import string
            stop_words = set(stopwords.words('english'))
            regular_punctuation = list(string.punctuation)


            def text_preprocessing(x):
                  filtered_sentence = []
                  word_tokens = word_tokenize(x)

                  for w in word_tokens:
                        if w not in chain(stop_words, regular_punctuation):
                        # we make sure that all words are written in lowercase
                              filtered_sentence.append(w.lower())

                  # Converting a list of strings back to a string
                  filtered_sentence = " ".join(filtered_sentence)
                  return filtered_sentence

            self.text_df['cleaned_website_text'] = self.text_df['cleaned_website_text'].apply(text_preprocessing)
            # print(self.text_df.head(3))
            print('data prepaired')

            return self.text_df
