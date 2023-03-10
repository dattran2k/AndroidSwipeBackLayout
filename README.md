[![](https://jitpack.io/v/dattran2k/AndroidSwipeBackLayout.svg)](https://jitpack.io/#dattran2k/AndroidSwipeBackLayout)

# AndroidSwipeBackLayout 📕

An library that can make your Fragment & Activity swipeable

![logo_git](https://user-images.githubusercontent.com/56917449/223653611-917dadf9-6a84-4897-bdc6-8771e6950109.svg )

## Gradle Dependency 🔨

```gradle 
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
	implementation 'com.github.dattran2k:AndroidSwipeBackLayout:1.0.4'
}
```
## Example 😎

I made some big tech app example : Facebook, Telegram, Tiktok, or you change change params dynamically

Download here : [**Google Play**](https://play.google.com/store/apps/details?id=com.dat.swipe_example) or look at the [**source code**](https://github.com/dattran2k/AndroidSwipeBackLayout.git)

📺 [**Youtube video demo**](https://youtu.be/JtcKXjldl6w)

![facebook](https://user-images.githubusercontent.com/56917449/223163217-647f25a0-a711-4c9f-b409-c72854b792ea.gif)
![telegram](https://user-images.githubusercontent.com/56917449/223163844-b24f949f-23ee-4d7d-9a64-674d01378907.gif)
![tiktok](https://user-images.githubusercontent.com/56917449/223163214-50fcfa8d-0544-43bc-bdc3-05006587310a.gif)
![shared element](https://user-images.githubusercontent.com/56917449/223163195-40e4c2a7-7a20-41ba-950b-f7d59e50b99c.gif)
![custom1](https://user-images.githubusercontent.com/56917449/223163230-3c81daaf-1b42-4a24-b2db-17b072fbafd3.gif)
![custom2](https://user-images.githubusercontent.com/56917449/223163222-35eb84ab-fd4e-4202-8f15-00d07e598a7e.gif)
## Usage
### Step 1. Implementation
```gradle
....

dependencies {
    implementation 'com.github.dattran2k:AndroidSwipeBackLayout:1.0.4'
}
```
### Step 2. Extends your Activity
```kotlin
class YourActivity : SwipeActivity() {
    private lateinit var binding: YourActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = YourActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
```
### Step 3. xml

Your root view must be single
```xml
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    ..................
</androidx.constraintlayout.widget.ConstraintLayout>
```

### Step 4. Theme

Set theme for your activity, this will make your activity have transparent background and see though when swipe

```xml
<item name="android:windowIsTranslucent">true</item>
<item name="android:windowBackground">@android:color/transparent</item>
```
## Documentation 📄
### 1. Activity
#### 1.1. Extends SwipeActivity
Activity
````kotlin
class YourActivity : SwipeActivity() {
    private lateinit var binding: YourActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = YourActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
````
Xml
````xml
<FrameLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
</FrameLayout>
````
#### 1.2. Without Extends SwipeActivity, you can wrap you root view with SwipeLayout in xml
Activity
````kotlin
class YourActivity : AppCompatActivity(), SwipeListener {
    private lateinit var binding: YourActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = YourActivityBinding.inflate(layoutInflater)
        // set your config here
        binding.root.setLateConfig(getSwipeConfig())
        setContentView(binding.root)
    }
    private fun getSwipeConfig(): SwipeLayoutConfig {
        return SwipeLayoutConfig.Builder()
            .listener(this)
            .position(SwipeDirection.LEFT_TO_RIGHT)
            .build()
    }
    override fun onSwipeStateChanged(state: Int) {

    }
    override fun onSwipeChange(percent: Float) {

    }
    override fun onSwipeOpened() {

    }
    override fun onSwipeClosed() {
        finish()
    }
    override fun onApplyScrim(alpha: Float) {

    }
}

````
#### 1.3. Without Extends SwipeActivity, you can wrap you root view with SwipeLayout in Activity
[**Check my SwipeBackActivity**](https://github.com/dattran2k/AndroidSwipeBackLayout/blob/master/SwipeLayout/src/main/java/com/dat/swipe_layout/SwipeBackActivity.kt)
### 2. Fragment, there are 2 ways to implement

First, sorry to say, if you are using navigation component, you can't use this lib because navigation component use replace() fragment by default, 
that means when you navigate to other fragment, your previous Fragment will run onDestroyView() so you can't see any things previous

Navigate between Fragment using add() will work

#### 2.1. Extends SwipeBackFragment

Fragment, wrap your root view with wrapSwipeLayout(yourRootView)

````kotlin

class SecondFragment : SwipeBackFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(layoutInflater)
        return wrapSwipeLayout(binding.root)
    }
}
````
Xml
````xml
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
</androidx.constraintlayout.widget.ConstraintLayout>
````
#### 2.2. Wrap your root view in xml
Fragment
````kotlin
class ThirdFragment : BaseFragment<FragmentThirdBinding>(FragmentThirdBinding::inflate),
    SwipeListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set config
        binding.container.setLateConfig(getSwipeConfig())
    }
    private fun getSwipeConfig(): SwipeLayoutConfig {
        return SwipeLayoutConfig.Builder()
            .listener(this)
            .position(SwipeDirection.LEFT_TO_RIGHT)
            .build()
    }
    override fun onSwipeStateChanged(state: Int) {}

    override fun onSwipeChange(percent: Float) {}

    override fun onSwipeOpened() {}

    override fun onSwipeClosed() {
        // your code when swipe close
        NavigationManager.getInstance().popBackStack()
    }
}
````
Xml
````xml
<com.dat.swipe_layout.swipe.SwipeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:id="@+id/container"
    android:layout_height="match_parent">
    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        ...
    </androidx.constraintlayout.widget.ConstraintLayout>
</com.dat.swipe_layout.swipe.SwipeLayout>
````
#### Update config

You can update your config when ever you want
```kotlin
fun updateConfig(){
    val myConfig = getSwipeConfig()
    // update config
    swipeLayout.setLateConfig(myConfig)
}
fun getSwipeConfig(): SwipeLayoutConfig {
    return SwipeLayoutConfig.Builder()
        .listener(this)
        .position(SwipeDirection.LEFT_TO_RIGHT)
        .build()
}

```
#### Warning
##### Your root views must me single child :
```
ConstraintLayout
FrameLayout
LinearLayout
.......
```
##### If your Fragment or Activity have Scrollable view, make sure it's your root view
```
NestedScrollView
ViewPager
RecyclerView
WebView
....
```
I used ```canScrollVertically()``` and ```canScrollHorizontally()``` to check child view can scroll or not

So if you don't do that, your "ScrollAbleView" cant' scroll at all

## Custom Config

| Parameter    | Type       | Default value              | Description |
| :--------    | :-------   | :------------------------- |:------------|
| `swipeDirection` | [**SwipeDirection**](https://github.com/dattran2k/AndroidSwipeBackLayout/blob/master/SwipeLayout/src/main/java/com/dat/swipe_layout/model/SwipeDirection.kt) | SwipeDirection.LEFT_TO_RIGHT| your swipe direction |
| `listener` | [**SwipeListener**](https://github.com/dattran2k/AndroidSwipeBackLayout/blob/master/SwipeLayout/src/main/java/com/dat/swipe_layout/model/SwipeListener.kt) | null| The swipe listener set by the user to respond to certain events in the sliding mechanism |
| `scrimColor` | `color` |   Color.BLACK| color of the background scrim |
| `scrimStartAlpha` | `Float`|1.0f | start alpha value for when your view start scroll ( 0.0f to 1.0f )|
| `scrimEndAlpha` | `Float`|0.0f| end alpha value for when your view stop draw scrim (0.0f  to 1.0f ) |
| `scrimThreshHold` | `Float`|0.0f| scrim only draw when scroll from 1f ->`scrimThreshHold` |
| `isFullScreenScrim` | `Boolean`|false | When layout draw scrim, it will be draw full screen, but it may reduce app performance useful when you activity or fragment transparent. |
| `isEnableScrim` | `Boolean`|true | this flag for check auto draw scrim |
| `distanceThreshold` | `Float`| 0.4f | The minimum viable distance the activity has to be dragged in order to be slinged off the screen, expressed as a percentage of the screen size (width or height). |
| `velocityThreshold` | `Float`|5000f| 	The velocity threshold at which the slide action is completed regardless of offset distance of the drag. |
| `touchDisabledViews` | `List<View>`|null| 	Views on which touching should not result in any swiping. |
| `touchSwipeViews` | `List<View>`|null| 	Views on which touching should not result in any swiping. |
| `....` | `....`|....| ..... |

[**View all config here**](https://github.com/dattran2k/AndroidSwipeBackLayout/blob/master/SwipeLayout/src/main/java/com/dat/swipe_layout/model/SwipeLayoutConfig.kt)


## Download example project ⬇️

```cmd
git clone https://github.com/dattran2k/AndroidSwipeBackLayout.git
```

## Feedback 📬

If you have any feedback, please reach out to me at trandat728@gmail.com, telegram : @Dat2030

Or my [**Facebook**](https://www.facebook.com/dat.20.30/)

I'm just small developer, you can contact me any time if you wanna help or have new idea for this lib

## Authors 👷

- [@dattran2k]https://github.com/dattran2k)



