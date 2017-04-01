# GooglePhoto
仿谷歌相册，实现了官方的全部功能

## 功能介绍
### 1.按照不同时间单位把照片分组
  - 分别以 `年/月/日` 为单位把照片分组并生成不同视图
  - 可以使用 `Toolbar` 的 `PopMenu` 或者缩放手势切换视图
  - 演示（不清晰请见谅）
  
  ![视图切换](https://github.com/jiajiaojie/google-photo/blob/master/GooglePhoto/app/art/function1.gif "视图切换")
  

### 2.列表右侧按钮可快速滑动
  - 拖动按钮左侧气泡会显示当前位置的照片的时间
  - 在快速滑动时会显示时间线
  
  ![快速滑动](https://github.com/jiajiaojie/google-photo/blob/master/GooglePhoto/app/art/function2.gif "快速滑动")
  
  
### 3.照片可以被选中，并带有动画
  - 点击可以单选
  - 长按并拖动可进行滑动多选，拖动到顶部或者底部时，列表会自动滚动
  
  ![滑动选择](https://github.com/jiajiaojie/google-photo/blob/master/GooglePhoto/app/art/function3.gif "滑动选择")
  
  
### 4.除了相机文件夹，还可以切换其他文件夹
  - 从 `PopMenu` 可以唤起文件夹列表，选择不同文件夹即可切换
  - 文件夹列表使用了 `BottomSheetBehavior` 可以两段滑动
  
  ![切换文件夹](https://github.com/jiajiaojie/google-photo/blob/master/GooglePhoto/app/art/function4.gif "切换文件夹")
  
  
## 感谢
  该相册实现中参考了以下优秀的项目
  - https://github.com/weidongjian/AndroidDragSelect-SimulateGooglePhoto
  - https://github.com/truizlop/SectionedRecyclerView
  - https://github.com/FutureMind/recycler-fast-scroll
