# HashMap和HashTable的区别

## 1，HashMap

## **继承的父类不同**

HashMap继承自AbstractMap类。但二者都实现了Map接口。
Hashtable继承自Dictionary类，Dictionary类是一个已经被废弃的类（见其源码中的注释）。父类都被废弃，自然而然也没人用它的子类Hashtable了。

## **HashMap线程不安全,HashTable线程安全**

HashMap线程不安全，所以效率会高，HashTable线程安全，所以效率会低，相比较而言**ConcurrentHashMap是线程安全**，而且效率比hashtable效率高

## 包含的contains方法不同

HashMap是没有contains方法的，而包括containsValue和containsKey方法；hashtable则保留了contains方法，效果同containsValue,还包括containsValue和containsKey方法。

## 是否允许null值

Hashmap是允许key和value为null值的，用containsValue和containsKey方法判断是否包含对应键值对；HashTable键值对都不能为空，否则包空指针异常。

## 扩容方式不同（容量不够）

HashMap 哈希扩容必须要求为原容量的2倍，而且一定是2的幂次倍扩容结果，而且每次扩容时，原来数组中的元素依次重新计算存放位置，并重新插入；
而Hashtable扩容为原容量2倍加1；