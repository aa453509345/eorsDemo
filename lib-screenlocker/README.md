### 集成步骤
#### 1.把lib-screenlocker库加入依赖
````
  implementation project(':lib-screenlocker')
````
#### 2.在App类的oncreate()方法中调用如下方法：
```
      ScreenLockerApi.getInstance().init(this);
      ScreenLockerApi.getInstance().inactive(this);
```

### API说明

#### 激活锁屏 lock(String path)  path为锁屏页面的路径如'/pages/demo/bmrichtext/index.js'
```
var screenLocker=weex.requireModule('screenLocker')
screenLocker.lock('/pages/demo/bmrichtext/index.js')
``` 

#### 关闭锁屏 unLock()
```
 var screenLocker=weex.requireModule('screenLocker')
 screenLocker.unLock();
```

#### 获取当前锁屏状态 getLockerStatus(callback) 
```
var screenLocker=weex.requireModule('screenLocker')
screenLocker.getLockerStatus((data)=>{
              this.status=data.data
           })
          
```
#### 激活电源锁  acquireWakeLock() 激活后锁屏后逻辑不会休眠  但会增加耗电 （部分机型可能不生效）
```
var screenLocker=weex.requireModule('screenLocker')
screenLocker.acquireWakeLock()

```

#### 关闭电源锁 releaseWakeLock()  释放电源锁 在unLock()时方法内部会自动释放电源锁
```
var screenLocker=weex.requireModule('screenLocker')
screenLocker.releaseWakeLock()
``` 