## [java中String、StringBuffer、StringBuilder的区别](https://www.cnblogs.com/xudong-bupt/p/3961159.html)

java中String、StringBuffer、StringBuilder是编程中经常使用的字符串类，他们之间的区别也是经常在面试中会问到的问题。现在总结一下，看看他们的不同与相同。

**1.可变与不可变**

　　String类中使用字符数组保存字符串，如下就是，因为有“final”修饰符，所以可以知道string对象是不可变的。

　　　　**private final char value[];**

　　StringBuilder与StringBuffer都继承自AbstractStringBuilder类，在AbstractStringBuilder中也是使用字符数组保存字符串，如下就是，可知这两种对象都是可变的。

　　　　**char[] value;**

***\*2.是否多线程安全\****

　　String中的对象是不可变的，也就可以理解为常量，**显然线程安全**。

　　AbstractStringBuilder是StringBuilder与StringBuffer的公共父类，定义了一些字符串的基本操作，如expandCapacity、append、insert、indexOf等公共方法。

　　StringBuffer对方法加了同步锁或者对调用的方法加了同步锁，所以是**线程安全的**。看如下源码：

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
1 public synchronized StringBuffer reverse() {
2     super.reverse();
3     return this;
4 }
5 
6 public int indexOf(String str) {
7     return indexOf(str, 0);        //存在 public synchronized int indexOf(String str, int fromIndex) 法
8 }
```

　　StringBuilder并没有对方法进行加同步锁，所以是**非线程安全的**。

 **3.StringBuilder与StringBuffer共同点**

　　StringBuilder与StringBuffer有公共父类AbstractStringBuilder(**抽象类**)。

　　抽象类与接口的其中一个区别是：抽象类中可以定义一些子类的公共方法，子类只需要增加新的功能，不需要重复写已经存在的方法；而接口中只是对方法的申明和常量的定义。

　　StringBuilder、StringBuffer的方法都会调用AbstractStringBuilder中的公共方法，如super.append(...)。只是StringBuffer会在方法上加synchronized关键字，进行同步。