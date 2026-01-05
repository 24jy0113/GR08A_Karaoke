```
brew services start mysql
or
brew services start mysql@8.0
🔹 1. ロクイン MySQL
mysql -u root -p
（パスワードなしenter）

mysql> show tables;
+----------------------+
| Tables_in_gr08a      |
+----------------------+
| category             |
| item                 |
| item_creating_status |
| item_option          |
| option               |
| option_detail        |
| order_detail         |
| order_detail_option  |
| orders               |
| reservation          |
| role                 |
| role_detail          |
| room                 |
| room_usage_status    |
| status               |
| store                |
| usage_history        |
| user                 |
+----------------------+
18 rows in set (0.00 sec)

mysql> INSERT INTO `option`(option_name)
    -> VALUES
    -> ('ドリンクサイズ'),
    -> ('ソース'),
    -> ('ドリンク氷');
Query OK, 3 rows affected (0.00 sec)
Records: 3  Duplicates: 0  Warnings: 0


mysql> SELECT * FROM category;
+-------------+-----------------------+
| category_id | category_name         |
+-------------+-----------------------+
|           1 | アルコール            |
|           2 | ソフトドリンク        |
|           3 | フードメニュー        |
|           4 | サイドメニュー        |
|           5 | デザート              |
|           6 | アルコール            |
|           7 | ソフトドリンク        |
|           8 | フードメニュー        |
|           9 | サイドメニュー        |
|          10 | デザート              |
+-------------+-----------------------+
10 rows in set (0.00 sec)

mysql> SELECT * FROM item;
+---------+--------------------+-------------+--------------+-------+-----------------+-------+
| item_id | item_name          | category_id | order_number | price | item_image      | stock |
+---------+--------------------+-------------+--------------+-------+-----------------+-------+
|       1 | ポテト             |           4 |            1 |   450 | potato.png      |     1 |
|       2 | イカフライ         |           4 |            2 |   230 | squid.png       |     1 |
|       3 | チーズピザ         |           3 |            3 |   600 | cheesepizza.png |     1 |
|       4 | メロンソーダ       |           2 |            4 |   300 | melonsoda.png   |     1 |
|       5 | 生ビール           |           1 |            5 |   600 | beer.png        |     1 |
|       6 | 唐揚げ             |           4 |            6 |   450 | karaage.png     |     1 |
+---------+--------------------+-------------+--------------+-------+-----------------+-------+
6 rows in set (0.00 sec)

mysql> SELECT * FROM item_creating_status;
+-------------------------+---------------------------+
| item_creating_status_id | item_creating_status_name |
+-------------------------+---------------------------+
|                       1 | 注文済み                  |
|                       2 | 調理済み                  |
|                       3 | 完了                      |
+-------------------------+---------------------------+
3 rows in set (0.00 sec)

mysql> SELECT * FROM item_option;
+-----------+---------+
| option_id | item_id |
+-----------+---------+
|         2 |       1 |
|         2 |       2 |
|         1 |       4 |
|         3 |       4 |
|         1 |       5 |
+-----------+---------+
5 rows in set (0.00 sec)
mysql> SELECT * FROM option;
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'option' at line 1
mysql> SELECT * FROM `option`;
+-----------+-----------------------+
| option_id | option_name           |
+-----------+-----------------------+
|         1 | ドリンクサイズ        |
|         2 | ソース                |
|         3 | ドリンク氷            |
+-----------+-----------------------+
3 rows in set (0.00 sec)

mysql> SELECT * FROM option_detail;
+------------------+-----------+--------------------+-------+
| option_detail_id | option_id | option_detail_name | price |
+------------------+-----------+--------------------+-------+
|                1 |         1 | S                  |   -50 |
|                2 |         1 | M                  |     0 |
|                3 |         1 | L                  |    50 |
|                4 |         2 | なし               |     0 |
|                5 |         2 | ケチャップ         |     0 |
|                6 |         2 | マヨネーズ         |     0 |
|                7 |         3 | なし               |     0 |
|                8 |         3 | あり               |     0 |
+------------------+-----------+--------------------+-------+
8 rows in set (0.00 sec)

mysql> SELECT * FROM order_detail;
Empty set (0.00 sec)

mysql> SELECT * FROM order_detail_option;
Empty set (0.00 sec)

mysql> SELECT * FROM orders;
+----------+-------+------------------+-------------------------+---------+------------------+
| order_id | total | receiving_number | item_creating_status_id | room_id | usage_history_id |
+----------+-------+------------------+-------------------------+---------+------------------+
|        3 |   680 |                1 |                       3 |       1 |                1 |
|        4 |  1200 |                2 |                       1 |       2 |                2 |
+----------+-------+------------------+-------------------------+---------+------------------+
2 rows in set (0.00 sec)

mysql> SELECT * FROM reservation;
+--------------------+----------------------------+--------------------------+
| reservation_number | reservation_reception_time | reservation_leaving_time |
+--------------------+----------------------------+--------------------------+
|                  1 | 10:00:00                   | 12:00:00                 |
|                  2 | 13:00:00                   | 16:00:00                 |
+--------------------+----------------------------+--------------------------+
2 rows in set (0.00 sec)

mysql> SELECT * FROM role;
+---------+--------------+
| role_id | role_name    |
+---------+--------------+
|       1 | フロント     |
|       2 | キッチン     |
|       3 | フロア       |
|       4 | 管理者       |
|       5 | フロント     |
|       6 | キッチン     |
|       7 | フロア       |
|       8 | 管理者       |
+---------+--------------+
8 rows in set (0.00 sec)

mysql> SELECT * FROM role_detail;
+---------+---------+
| role_id | user_id |
+---------+---------+
|       3 | 000001  |
|       2 | 000002  |
|       1 | 000003  |
+---------+---------+
3 rows in set (0.00 sec)

mysql> SELECT * FROM room;
+---------+-------------+--------------+
| room_id | room_number | store_number |
+---------+-------------+--------------+
|       1 |         101 |            1 |
|       2 |         102 |            1 |
|       3 |         103 |            1 |
|       4 |         101 |            1 |
|       5 |         102 |            1 |
|       6 |         103 |            1 |
+---------+-------------+--------------+
6 rows in set (0.00 sec)

mysql> SELECT * FROM room_usage_status;
+---------+-------------------+----------------+--------------+-----------+--------------------+
| room_id | alcohol_provision | reception_time | leaving_time | status_id | reservation_number |
+---------+-------------------+----------------+--------------+-----------+--------------------+
|       1 |                 0 | 10:00:00       | 12:00:00     |         4 |                  1 |
|       2 |                 1 | 13:00:00       | NULL         |         3 |                  2 |
+---------+-------------------+----------------+--------------+-----------+--------------------+
2 rows in set (0.00 sec)

mysql> SELECT * FROM status;
+-----------+--------------+
| status_id | status_name  |
+-----------+--------------+
|         1 | 空き         |
|         2 | 予約         |
|         3 | 受付済み     |
|         4 | 会計済み     |
|         5 | 空き         |
|         6 | 予約         |
|         7 | 受付済み     |
|         8 | 会計済み     |
+-----------+--------------+
8 rows in set (0.01 sec)

mysql> SELECT * FROM store;
+--------------+--------------------+
| store_number | store_name         |
+--------------+--------------------+
|            1 | 東京都新宿店       |
|            2 | 東京都品川店       |
|            3 | 埼玉県川越店       |
|            4 | 東京都新宿店       |
|            5 | 東京都品川店       |
|            6 | 埼玉県川越店       |
+--------------+--------------------+
6 rows in set (0.00 sec)


mysql> SELECT * FROM usage_history;
+------------------+------------+----------------+--------------+
| usage_history_id | date       | reception_time | leaving_time |
+------------------+------------+----------------+--------------+
|                1 | 2025-11-01 | 10:00:00       | 12:00:00     |
|                2 | 2025-11-01 | 13:00:00       | 16:00:00     |
|                3 | 2025-11-01 | 10:00:00       | 12:00:00     |
|                4 | 2025-11-01 | 13:00:00       | 16:00:00     |
+------------------+------------+----------------+--------------+
4 rows in set (0.00 sec)

mysql> SELECT * FROM user;
+---------+----------+-----------------+---------------------+
| user_id | password | user_name       | last_login_time     |
+---------+----------+-----------------+---------------------+
| 000001  | aaaaaaaa | 田中太郎        | 2025-12-17 10:00:00 |
| 000002  | bbbbbbbb | 佐藤太一        | 2025-09-07 20:00:00 |
| 000003  | cccccccc | 鈴木のりこ      | 2025-10-27 09:30:00 |
+---------+----------+-----------------+---------------------+
3 rows in set (0.00 sec)

```
