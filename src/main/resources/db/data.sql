INSERT INTO category (category_id, name)
VALUES (1, '미니SUV'),
       (2, '준중형 SUV'),
       (3, '대형RV'),
       (4, '중형 트럭'),
       (5, '대형 RV'),
       (6, '경형 RV'),
       (7, '중형 SUV');

INSERT INTO car (car_id, manufacturer, model, production_year, rental_status)
VALUES (1, '현대', '코나', 2024, 'AVAILABLE'),
       (2, '현대', '아이오닉', 2024, 'AVAILABLE'),
       (3, '현대', '스타리아', 2022, 'AVAILABLE'),
       (4, '현대', '포터', 2024, 'AVAILABLE'),
       (5, '현대', '투싼', 2023, 'AVAILABLE'),
       (6, 'KIA', '카니발', 2021, 'AVAILABLE'),
       (7, 'KIA', '레이', 2022, 'LOST'),
       (8, 'KIA', '봉고3', 2023, 'UNAVAILABLE'),
       (9, 'KIA', '쏘렌토', 2024, 'UNDER_REPAIR');

INSERT INTO car_category (car_category_id, car_id, category_id)
VALUES (1, 1, 1),
       (2, 2, 2),
       (3, 3, 3),
       (4, 4, 4),
       (5, 5, 2),
       (6, 6, 5),
       (7, 7, 6),
       (8, 8, 4),
       (9, 9, 7);
