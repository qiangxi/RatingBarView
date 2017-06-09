# RatingBarView
继承LinearLayout自定义的RatingBarView,提供可高度自定义的属性,简单易用

### 效果图如下:
![效果图](http://a2.qpic.cn/psb?/V139PLsQ3siJoP/374s9j7S7uPjfzK160VKDrDWxSh2NKFOMvuXca1aAqA!/b/dHUAAAAAAAAA&ek=1&kp=1&pt=0&bo=nQGqAgAAAAADFwY!&tm=1497006000&sce=60-4-3&rf=viewer_4)
### 使用方式:
查看最新版本点击[这里]()
##### gradle:
```groovy
compile 'com.qiangxi.ratingbarview:ratingbarview:1.0.2'
```
##### maven:
```maven
<dependency>
  <groupId>com.qiangxi.ratingbarview</groupId>
  <artifactId>ratingbarview</artifactId>
  <version>1.0.2</version>
  <type>pom</type>
</dependency>
```
### 各个自定义属性的作用
属性 | 对应的方法|作用
---|---|---
totalCount |setRatingCount(int count)| 子view数量总数量
selectedCount |setSelectedCount(int selectedCount)| 选中的数量
selectedDrawable|setSelectedIconResId(@DrawableRes int selectedIconResId)| 选中图片资源id
normalDrawable|setNormalIconResId(@DrawableRes int normalIconResId) | 未选中图片资源id
clickable |setClickable(boolean clickable)|RatingBarView是否可点击
childMargin | setChildMargin(int childMargin) |子view之间的左右margin
childPadding |setChildPadding(int childPadding) |子view的padding
childDimension|setChildDimension(int childDimension)|每个子view的宽高，宽=高
### 关于issues
欢迎提出issue,我会尽量满足大家的需求.
### 最后的最后
如果这个library帮助了你,还请希望给个Star或Fork,不胜感激!
