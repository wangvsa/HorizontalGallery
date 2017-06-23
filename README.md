# HorizontalGallery
Android horizontal scrollview for displaying images.

## Setup
**Step 1.** Add the JitPack repository to your root build.gradle:
```java
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
**Step 2.** Add the dependency
```java
dependencies {
  compile 'com.github.wangvsa:HorizontalGallery:v0.1'
}
```


## Usage

**Step 1.** Define you layout file
```xml
<io.github.wangvsa.horizontalgallerylib.HorizontalGallery
  android:id="@+id/horizontal_gallery"
  android:layout_width="match_parent"
  android:layout_height="200dp" />
```

**Step 2.** Setup HorizontalGallery and HorizontalGalleryAdapter
```java
// Your images uri
List<String> images = new ArrayList<>();    
images.add("drawable://"+R.drawable.pic1);              // from drawables (non-9patch images)
images.add("content://media/external/images/media/13")  // from content provider
images.add("assets://image.png")                        // from assets

HorizontalGallery gallery = (HorizontalGallery) findViewById(R.id.horizontal_gallery);
HorizontalGalleryAdapter adapter = new HorizontalGalleryAdapter(this, images);
gallery.setHorizontalGalleryAdapter(adapter);
```

HorizontalGallery uses [Universal Image Loader](https://github.com/nostra13/Android-Universal-Image-Loader). 
Acceptable URIs example:
```java
"http://site.com/image.png"                     // from Web
"file:///mnt/sdcard/image.png"                  // from SD card
"content://media/external/images/media/13"      // from content provider
"assets://image.png"                            // from assets
"drawable://" + R.drawable.img                  // from drawables (non-9patch images)
```
