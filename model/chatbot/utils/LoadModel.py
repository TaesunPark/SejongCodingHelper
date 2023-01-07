from chatbot.config.DatabaseConfig import *
from chatbot.utils.Database import Database
from chatbot.utils.Preprocess import Preprocess
from chatbot.models.intent.IntentModel import IntentModel
from chatbot.models.ner.NerModel import NerModel
from chatbot.utils.GiveAnswer import GiveAnswer

# 전처리 객체 생성
p = Preprocess(word2index_dic='../train_tools/dict/chatbot_dict.bin',
               userdic='../utils/user_dic.tsv')

# 질문/답변 학습 DB 연결 객체 생성
db = Database(
    host=DB_HOST, user=DB_USER, password=DB_PASSWORD, db_name=DB_NAME
)
db.connect()  # DB 연결

intent = IntentModel(model_name='../models/intent/intent_model.h5', preprocess=p)
ner = NerModel(model_name='../models/ner/ner_model.h5', preprocess=p)

question = GiveAnswer(db=db)

cnt = 0
while cnt < 10:
    print("cnt = ", cnt)

    # 사용자 질문
    print("input = ")
    msg = input()
    language = 'c'
    recommend_dict = [{'id': 30, 'intent': '정의', 'title': '조건문'},
    # recommend_dict = [{'id': 1, 'intent': '인사', 'title': '헬로'},
                    {'id': 31, 'intent': '정의', 'title': '관계연산자'},
                    {'id': 34, 'intent': '정의', 'title': 'if문'},
                    {'id': 22, 'intent': '정의', 'title': '변수 선언'}]
    answer = question.give_answer(msg, intent, ner, language, recommend_dict)
    cnt += 1
    print("답변: ", answer)

db.close()  # DB 연결 끊음
