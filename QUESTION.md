Q1:

发送消息是一个进程，称为发送进程；发送进程结束后，才会启动消费进程；

Q2:

在本题里，每个Consumer对应的Queue，如果绑定了某个Topic，则要求消费到这个Topic的所有消息；

Consumer1(绑定到了Topic)，需要消费M11,M12,M13,M14

Consumer2(绑定到了Topic)，也需要消费M11,M12,M13,M14

每个Consumer相对独立，各自保存各自已经消费到的位置；

每个Consumer都会attach到不同的Queue

Q3:测试会存在Topic和Queue名字一样的情况吗

肯定不会的

Q4:


不同topic，不同queue，topic与queue之间都不用考虑顺序的

Q5:


