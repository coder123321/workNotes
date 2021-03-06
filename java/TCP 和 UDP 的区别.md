# TCP 和 UDP 的区别

要想理解 TCP 和 UDP 的区别，首先要明白什么是 TCP，什么是 UDP

TCP 和 UDP 是传输层的两个协议

> TCP 和 UDP 的区别

- TCP 是面向连接的，UDP 是面向无连接的
- UDP程序结构较简单
- TCP 是面向字节流的，UDP 是基于数据报的
- TCP 保证数据正确性，UDP 可能丢包
- TCP 保证数据顺序，UDP 不保证

## 1. UDP

> UDP 的特点

- 沟通简单，不需要大量的数据结构，处理逻辑和包头字段
- 轻信他人。它不会建立连接，但是会监听这个地方，谁都可以传给它数据，它也可以传给任何人数据，甚至可以同时传给多个人数据。
- 愣头青，做事不懂变通。不会根据网络的情况进行拥塞控制，无论是否丢包，它该怎么发还是怎么发

因为 UDP 是"小孩子"，所以处理的是一些没那么难的项目，并且就算失败的也能接收。基于这些特点的话，UDP 可以使用在如下场景中

> UDP 的主要应用场景

- 需要资源少，网络情况稳定的内网，或者对于丢包不敏感的应用，比如 DHCP 就是基于 UDP 协议的。
- 不需要一对一沟通，建立连接，而是可以广播的应用。因为它不面向连接，所以可以做到一对多，承担广播或者多播的协议。
- 需要处理速度快，可以容忍丢包，但是即使网络拥塞，也毫不退缩，一往无前的时候

基于 UDP 的几个例子

- 直播。直播对实时性的要求比较高，宁可丢包，也不要卡顿的，所以很多直播应用都基于 UDP 实现了自己的视频传输协议
- 实时游戏。游戏的特点也是实时性比较高，在这种情况下，采用自定义的可靠的 UDP 协议，自定义重传策略，能够把产生的延迟降到最低，减少网络问题对游戏造成的影响
- 物联网。一方面，物联网领域中断资源少，很可能知识个很小的嵌入式系统，而维护 TCP 协议的代价太大了；另一方面，物联网对实时性的要求也特别高。比如 Google 旗下的 Nest 简历 Thread Group，推出了物联网通信协议 Thread，就是基于 UDP 协议的

## 2. TCP

> TCP 的包头有哪些内容，分别有什么用

- 首先，源端口和目标端口是不可少的。
- 接下来是包的序号。主要是为了解决乱序问题。不编好号怎么知道哪个先来，哪个后到
- 确认序号。发出去的包应该有确认，这样能知道对方是否收到，如果没收到就应该重新发送，这个解决的是不丢包的问题
- 状态位。SYN 是发起一个链接，ACK 是回复，RST 是重新连接，FIN 是结束连接。因为 TCP 是面向连接的，因此需要双方维护连接的状态，这些状态位的包会引起双方的状态变更
- 窗口大小，TCP 要做流量控制，需要通信双方各声明一个窗口，标识自己当前的处理能力。

通过对 TCP 头的解析，我们知道要掌握 TCP 协议，应该重点关注以下问题：

- 顺序问题
- 丢包问题
- 连接维护
- 流量控制
- 拥塞控制

所有的问题，首先都要建立连接，所以首先是连接维护的问题

TCP 的建立连接称为三次握手，可以简单理解为下面这种情况

> A：您好，我是 A
> B：您好 A，我是 B
> A：您好 B

至于为什么是三次握手我这里就不细讲了，可以看其他人的博客，总结的话就是通信双方全都有来有回

对于 A 来说它发出请求，并收到了 B 的响应，对于 B 来说它响应了 A 的请求，并且也接收到了响应。

> TCP 的三次握手除了建立连接外，主要还是为了沟通 TCP 包的序号问题。

A 告诉 B，我发起的包的序号是从哪个号开始的，B 同样也告诉 A，B 发起的 包的序号是从哪个号开始的。