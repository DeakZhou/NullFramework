# NullFramework项目介绍文档

## 前言

> 再介绍该项目之前，先来说说为何会写这么一个东西？屈指算来，做Android开发已经有5年了，就技术而言，这5年可以说进步很大，而我认为其中变化最大的应该是编程思维的转变，再也不是一拿到需求就开始敲代码，而是会去考虑一些别的因素，比如：随着业务逻辑日趋复杂，如何保证程序逻辑清晰、能否将一些常用的模块抽象成与业务无关的公共类库、能否将系统已有的组件进行二次封装，做到用最少的代码实现相同的功能......而该项目就是基于以上这些问题日积月累形成的一个没有业务只有架子的空项目。

## NullFramework内容

### 一、项目架构

项目整体采用MVP的思想进行设计，将Model与View彻底进行了解藕，使得View只做显示，Model负责获取数据，两者互不干扰。

图

### 二、AppManager

在我们开发应用的时候，经常会有很多很多的activity，这时候，我们就需要一个activity栈来帮忙管理activity。而该类使用一个单例模式去管理，使得整个应用在任何地方都可以访问这个activity栈，这样就方便了应用的操作，本身提供了退出应用、关闭指定activity、关闭全部activity等方法。

```java
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  // 添加Activity到堆栈  
  AppManager.getInstance().addActivity(this);
  ...
}
    
@Override  
protected void onDestroy() {  
  super.onDestroy();  
  // 结束Activity从堆栈中移除  
  AppManager.getAppManager().finishActivity(this);
  ...
}  
```
### 三、BaseAdapter

在Android中展示列表数据，Adapter是必须要写的，但传统的Adapter过于复杂，需要重载很多不需要的方法，并且提供的api也并不完善，于是我对其进行了二次封装，直接上代码：

```java
public class TestAdapter extends BaseAdapter<User> {
  public TestAdapter(Context pContext, int layoutId) {
    super(pContext, layoutId);
  }	
	
  @Override
  public void convertView(BaseViewHolder viewHolder, User user) {
    //viewHolder.showImage(id, value).setOnClickListener(id, onClickListener);
  }
}
```
哇，adapter竟然如此简单了，是的，不止如此，该Adapter还支持多样式布局，并且丰富了api：

```java
public BaseAdapter(Context pContext, Map<Integer, Integer> items) {
  this.mContext = pContext;
  this.items = items;
}
//重新设置数据源
public void setData(List<T> pData) {...}
//追加数据源
public void addData(List<T> pData) {...}
//移除某个数据
public boolean removeData(T t) {...}
//追加一个数据
public void addData(T t) {...}
//头部添加一个数据
public void addDataToFirst(T t) {...}
//清空数据
public void clearData() {...}
//判断是否为空
public boolean isEmptyData() {...}
//禁止滑动
public void setScrolling(boolean scroll){...}
//RecycleView没有提供OnItemClicklistener，可通过该方法监听行点击
protected View.OnClickListener onClickListener;
public void setOnClickListener(View.OnClickListener onClickListener) {
  this.onClickListener = onClickListener;
}
```	
BaseViewHolder中的方法可根据业务自行扩展：

```java
public BaseViewHolder setVisibility(int viewId, int visibility) {  
  View view = getView(viewId);
  view.setVisibility(visibility);
  return this;
}
public BaseViewHolder setViewTag(int viewId, Object tag){
  View view = getView(viewId);
  view.setTag(tag);
  return this;
}
```
### 四、功能强大的FxRelativeLayout

Android设备显示一个Activity，其实是将xml文件实例化为一个View，将View渲染到窗口上从而显示出来的，而FxRelativeLayout是先将xml实例化后的View加进来，再把自己本身渲染到界面上，由于FxRelativeLayout是自己实现的，因此可以事先将一些通用的View添加进去，目前FxRelativeLayout有如下View层：

```java
  ToolBar //应用顶部的导航栏
  ProgressDialog //非模态进度条
  LodingDialog //模态进度条
  ErrorView //错误View，当界面出异常无数据时显示
  ShadowView //阴影
```
### 五、简单易用的动态权限申请

Android6.0后，出于安全考虑，加入了动态权限申请，但使用及其麻烦，于是相关的第三方框架如雨后春笋般冒了出来，经过权衡，我选择了EasyPermission，但发现用起来还是不够友好，于是进行二次封装，请看封装且申请权限的代码：

```java
//第一步，申请
requestPermission(PermissionCode.REQUEST_CALL);//申请打电话权限
//第二步，重载结果函数
public void handlePermissionResult(int code, boolean isSuccessed){
  if(isSuccessed){
    //成功
  } else {
    //失败
  }
}
```

### 六、通过RouterSchema跳转Activity

首先我们先来思考一个问题，Android提供了startActivity()方法，为何还需要路由跳转？

- **可取代使用startActivity、startActivityForResult跳转的情景，便于协同开发**
- **通过一串url可任意跳转到指定界面，使用应尽可能简单**
- **支持各种类型参数传递、界面转场动画**
- **可获取起跳界面的路径和当前界面路径，以便支持后期埋点等需求**
- **支持从H5到Native，Native到H5，这是Hybrid开发模式中常用到的需求**
- **简化代码，数行跳转代码精简成一行代码**

##### Url格式

```java
scheme://host/path
```
- **scheme：APP内自己定义的，不过这个在H5内跳Native时，需要和前端协商定义好，本地间的跳转可以随自己定义，比如：activity**
- **host：这个尽可能按各个Activity的所在模块命名**
- **path：各个Activity的功能名**

##### 使用

>第一步 定义 url

```java
mol://user/login
```

>第二步 注册 LoginActivity

```xml
<activity android:name=".LoginActivity">
  <intent-filter>
    <action android:name="android.intent.action.VIEW"/>
    <category android:name="android.intent.category.DEFAULT"/>
	
    <data android:scheme="mol"/>
    <data android:host="user"/>
    <data android:path="/login"/>
  </intent-filter> 
</activity>
```

>第三步，该openActivity有多个重载函数，可满足所有跳转需求，下面举两个例子

```java
openActivity("mol://user/login")
openActivity("mol://user/login", 1001, "username", "张三", "password", "123456")
```

### 七、自定义的ToolBar，使用更加灵活

目前支持的设置

```java
public class ToolBarData{
  private String title; //标题
  private int navigationLeftIcon; //左导航,默认back
  private int navigationRightIcon; //图标右导航
  public String navigationRightText; //文字右导航
  private int backgroundColor; //背景色
  private boolean isShowExitIcon; //退出（网页上返回和退出是分开的）
}
```
用法

```java
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  mToolBarData.setTitle("登录");
  mToolBarData.setNavigationRightText("注册");
  requestToolBar();
}
```

### 八、无需引入第三方类库，一个类搞定注解初始化控件

AnnotationsUtils

```java
AnnotationsUtils {
  @Target(ElementType.FIELD)//表示用在字段上
  @Retention(RetentionPolicy.RUNTIME)//表示在生命周期是运行时
  public @interface ViewInject {
	  int value() default 0;
  }
	
  /**
  * 解析注解
  */
  public void autoInjectAllField(Object object, View view) {
    try {
	  Field[] fields = object.getClass().getDeclaredFields();//获得Activity中声明的字段
	  for (Field field : fields) {
	    // 查看这个字段是否有我们自定义的注解类标志的
		if (field.isAnnotationPresent(ViewInject.class)) {
		  ViewInject inject = field.getAnnotation(ViewInject.class);
		  int id = inject.value();
		  if (id > 0) {
		    field.setAccessible(true);
		    field.set(object, view.findViewById(id));//给我们要找的字段设置值
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
```
使用方法

```java
@AnnotationsUtils.ViewInject(R.id.btnTest)
private Button mBtnTest;
```

### 九、通用样式表，使xml布局文件更简单

通过对不同的View定义不同的style，使xml布局文件更简单，下面分别定义了View和LinearLayout的样式：

```xml
<style name="View_UnifyStyle">
  <item name="android:layout_width">wrap_content</item>
  <item name="android:layout_height">wrap_content</item>
</style>
	
<style name="Linearlayout_UnifyStyle" parent="View_UnifyStyle">
  <item name="android:layout_width">match_parent</item>
  <item name="android:orientation">horizontal</item>
  <item name="android:gravity">center_vertical</item>
</style>
	
<style name="Box_Linearlayout_UnifyStyle" parent="View_UnifyStyle">
  <item name="android:layout_width">match_parent</item>
  <item name="android:background">@color/white</item>
  <item name="android:orientation">horizontal</item>
  <item name="android:gravity">center_vertical</item>
  <item name="android:paddingLeft">@dimen/view_padding</item>
  <item name="android:paddingRight">@dimen/view_padding</item>
  <item name="android:paddingTop">@dimen/view_padding_middle</item>
  <item name="android:paddingBottom">@dimen/view_padding_middle</item>
</style>
```
假如需要一个带内边距的LinearLayout，只需要这样：

```xml
<LinearLayout
  style="@style/Box_Linearlayout_UnifyStyle">    
</LinearLayout>
```

### 十、一些小改进

1、通过对Toast的重新实现，Toast的样式不会随着手机rom的不同而呈现不周的样式
2、通过自定义onClick事件，解决了快速点击多次响应的问题

