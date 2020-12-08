

# CSS垂直居中，你会多少种写法

## **水平居中**

- #### **块级元素水平居中**

```css


.container {
  height: 300px;
  width: 300px;
  border: 1px solid red;
}

.content {
  width: 10rem;
  border: 1px solid green;
  margin: 0 auto;
}
```

- #### **内联元素水平居中**

```css
.container {
  height: 300px;
  width: 300px;
  border: 1px solid red;
  text-align: center;
}

.content {
  display: inline-block;
  border: 1px solid green;
}
```



## **水平垂直居中**

- #### **flex布局**

```css
.container {
    width: 300px;
    height: 300px;
    border: 1px solid red;
    display: -webkit-flex;
    display: flex;
    // 关键属性
    align-items: center; // 垂直居中
    justify-content: center // 水平居中
  }

  .content {
    border: 1px solid green;
  }
```

- ### **margin+ position:absolute布局**

```css

.container {
  width: 300px;
  height: 300px;
  position: relative;
  border: 1px solid red;
}

.content {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  width: 200px;
  height: 100px;
  margin: auto;
  border: 1px solid green;
}
```

- ### **transform + absolute**

```css

.container {
  width: 300px;
  height: 300px;
  position: relative;
  border: 1px solid red;
}

.content {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  border: 1px solid green;
}
```

- **absolute+margin负值**

```css

.container {
  width: 300px;
  height: 300px;
  position: relative;
  border: 1px solid red;
}

.content {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 200px;
  height: 100px;
  margin-top: -50px;
  margin-left: -100px;
  border: 1px solid green;
}
```

- ### **absolute + calc**

```css

.container {
  width: 300px;
  height: 300px;
  border: 1px solid red;
  text-align: center;
  position: relative;
}

.content {
  position: absolute;
  border: 1px solid green;
  width: 200px;
  height: 100px;
  left: calc(50% - 100px);
  top: calc(50% - 50px);
}
```

