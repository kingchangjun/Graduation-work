import pandas as pd
from surprise import Dataset, Reader, SVD
from surprise.model_selection import cross_validate
import pickle

# 데이터 로드 (사용자 ID, 제품 ID, 평점 데이터)
data = pd.read_csv("purchase_data.csv")  # 예제 데이터

reader = Reader(rating_scale=(1, 5))
dataset = Dataset.load_from_df(data[['user_id', 'product_id', 'rating']], reader)

#  SVD 기반 추천 모델 학습
trainset = dataset.build_full_trainset()
model = SVD()
model.fit(trainset)

# 학습된 모델 저장
with open("recommendation_model.pkl", "wb") as f:
    pickle.dump(model, f)

print("추천 모델 학습 완료 및 저장됨!")
