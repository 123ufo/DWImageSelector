# DWImageSelector
### 一个用于Android的图片选择库 三行代码就能实现图片选择(单选、多选<1-9张>，拍照<1张>)

### 第一步

    compile 'com.ufo:DWImageSelector:0.9.1'

### 第二步：
##### 图片选择:

    DWImages.getImages(this, DWImages.ACTION_ALBUM, 6);


##### 拍照选择:

    DWImages.getImages(this, DWImages.ACTION_CAMERA, 1);

### 第三步:
##### 结果获取:

      @Override
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           super.onActivityResult(requestCode, resultCode, data);

            //就加上这行代码
           DWImages.parserResult(requestCode, data, new DWImages.GetImagesCallback() {
               @Override
               public void onResult(List<String> images) {
                    //这里返回选择的图片路径
               }
           });
       }


 ### 具体更多操作，请看工程里的Demo,欢迎大家多多尝试，多挑毛病.

 ### 扫我安装休验Demo

<img src="https://github.com/123ufo/DWImageSelector/blob/master/imgs/ic_qrcode.jpg?raw=true" width="280"/>