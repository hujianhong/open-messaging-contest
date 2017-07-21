性能待优化的地方：

1.写磁盘的地方，由即时写改为批量写（定时，满写），（划分成多个文件）
2.读磁盘的地方，缓存读，(每次从磁盘批量读一堆数据出来）

3.建立磁盘文件索引，offset,position

4.根据时间戳写临时文件，避免丢失消息，topic_timestamp 或 queue_timestamp

5.或者生成消息的offset，写临时文件，topic_offset 或 queue_offset来避免消息丢失