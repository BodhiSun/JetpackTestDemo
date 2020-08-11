# JetpackTestDemo（Google推荐的Jetpack架构组件学习，及MVVM项目架构搭建）


## 一.Jetpack架构组件介绍

### 1. LifeCycle    
功能介绍：    
用于创建可感知(Activity,Fragment,Service,Application)生命周期的自定义组件类，解耦的同时也避免造成内存泄露。
原理是通过LifeCycleOwner（被观察者）和LifeCycleObserver(观察者)这两个类实现观察者模式，完成对页面生命周期的监听。   
      
组件导入：implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
   
用法：
###### Activity&Fragment   
新版本Activity/Fragment中默认实现了LifeCycleOwner，所以自定义组件直接实现LifeCycleObserver接口即可，并在需要感知生命周期的方法上添加@OnLifecycleEvent(Lifecycle.Event.ON_XXX)注解,
当页面声明周期发生变化会自动调用这些方法。最后在Activity/Fragment中通过调用getLifecycle().addObserver(xxx自定义组件类)，将观察者和被观察者绑定起来完成生命周期的检测。

###### Service
Service没有实现LifeCycleOwner接口,要想感知Service生命周期,需要使用他的实现了LifeCycleOwner接口的子类LifecycleService。所以我们创建自己的Service然后继承LifecycleService即可，
其他用法与Activity一样。

###### Application
使用ProcessLifecycleOwner可以感知应用程序的生命周期，像Activity、Service一样先自定义一个类并实现LifeCycleObserver接口，在方法上添加具体要感知的生命周期注解，然后在自定义Application的
onCreate()方法中调用ProcessLifecycleOwner.get().getLifecycle().addObserver(new xxx自定义组件类())即可。根据Application不同生命周期的回调可以实现应用前后台状态切换的监听。

### 2. Navigation:
功能介绍：    
主要适用于单Activity包含多个Fragment的情况，用于解决Fragment切换，不同Fragment中的Appbar管理,Fragment之间切换动画和参数传递等问题。    

组件导入：    
implementation 'androidx.navigation:navigation-fragment:2.1.0'   
implementation 'androidx.navigation:navigation-ui:2.1.0'

##### Navigation主要包含如下元素：

###### Navigation Graph 
它是一种新的xml类型的资源文件，需在res目录下创建navigation文件夹，然后在该文件夹右键New->Navigation Resource File创建根标签为navigation的xml文件。在该文件中添加所有的要切换fragment,
配置activity初始显示fragment的app:startDestination属性,配置某个fragment跳转到另一个fragment的action动作标签(包含android:id动作id,app:destination跳转的目的地,enterAnim、exitAnim、
popEnterAnim、popExitAnim跳转动画)。

###### NavHostFragment
它是一个在Activity的xml中配置的特殊的fragment,它相当于Navigation Graph中配置的其他fragment的容器。设置android:id属性，设置android:name="androidx.navigation.fragment.NavHostFragment"
属性，设置app:defaultNavHost="true"属性(自动处理系统返回键)，设置app:navGraph="@navigation/xxx"属性指向创建的Navigation Graph。

###### NavController  
它是一个java对象，用于在Activity代码中对Fragment切换事件进行监听，用于在Fragment代码中实现Navigation Graph中配置有跳转逻辑action的具体切换工作。   
Activity中监听fragment切换方法：Navigation.findNavController(this,NavHostFragment配置的id).addOnDestinationChangedListener(new NavController.OnDestinationChangedListener(){...})。   
Fragment中切换动作实现：Navigation.findNavController(view).navigate(Navigation Graph配置的action id，跳转bundle参数);   
或者：Navigation.createNavigateOnClickListener(Navigation Graph配置的action id，跳转bundle参数)。

**它们三者的关系是：NavController负责切换Navigation Graph中配置的某个fragment并把它展示在NavHostFragment上。fragment跳转可以配合使用safe args插件更安全的实现参数传递**    

##### NavigationUI介绍：    
主要为了使Navigation和App Bar相结合，使AppBar中的菜单按钮能够和Navigation Graph中配置的fragment关联起来，并实现点击菜单按钮页面跳转。

##### Navigation DeepLink介绍：    
主要为了点击通知通过PendingIntent方式跳转到指定通知打开fragment，或者通过URL方式当浏览器加载某个页面提示在应用内打开，跳转到指定的页面

### 3. ViewModel:
功能介绍:   
作为视图(View)和数据模型(Model)之间的桥梁，专门用于存放程序页面所需的数据，使页面只负责数据展示和接收用户交互动作。   
ViewModel独立于页面存在，屏幕旋转不会使ViewModel销毁，ViewModel所持有的数据仍然存在。所以使用ViewModel时不要传入context相关的引用。  

组件导入：implementation "androidx.lifecycle:lifecycle-viewmodel:2.2.0"   

用法：   
ViewModel使用方法也很简单，自定义一个类然后继承ViewModel，由于ViewModel是抽象类所以要实现一个onCleared()方法，该方法会在与之对应的Activity销毁时被系统自动调用，主要可用于资源释
放相关操作。然后在相关Activity中通过new ViewModelProvider(this).get(自定义ViewModel.class)方法实例化ViewModel，此处的this指代的不是activity/context,指代的是ViewModelStoreOwner
因为上层的Activity(Fragment也是)默认实现了ViewModelStoreOwner接口。   
AndroidViewModel是ViewModel的子类，它构造函数中带有Application，如在ViewModel中需要使用Context可以使用AndroidViewModel。

### 4. LiveData:
功能介绍:   
LiveData通常配合ViewModel使用，将LiveData放在ViewModel中，包装ViewModel中对外提供的数据。   
LiveData本身相当于一个数据容器，并且是可被观察的数据容器类，它将数据包装起来成为被观察者，当数据发生变化观察者就能获得通知。所以ViewModel用来存放页面相关的数据，而LiveData用来包装
数据使之能够感知数据变化，结合起来就变成了在页面中获得ViewModel中用LiveData包装起来的数据，当数据发生变化页面自动收到通知，Amazing！   

组件导入：implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'   

用法：
由于LiveData是抽象类无法直接使用，我们可以使用它的直接子类MutableLiveData，在自定义ViewModel中定义MutableLiveData<数据>对象,并暴露对外获取的方法，在页面上通过ViewModelProvider获取
自定义的ViewModel对象，然后通过暴露的方法获取MutableLiveData<数据>对象，最后通过liveData.observe(this, new Observer<Integer>() {...}获取实时变化的数据，也可以通过liveData.setValue()在UI线程更改liveData中包装的数据，或通过liveData.postValue()在非UI线程中更改数据。   
liveData.observe()方法中第一个参数指的是LifecycleOwner，第二个参数是Observer，方法内部调用的是lifecycleOwner.getLifecycle().addObserver(lifecycleObserver)，将observer和页面的生命周期关联起来。所以liveData可以感知生命周期，在页面处于活动状态时才可以收到LiveData的数据变化通知，销毁状态下自动清除与页面的关联。   
liveData.observeForever()方法可以使页面不管处于何种状态下都能收到数据变化通知，但是在恰当位置要加上与之对应的liveData.removeObserver()方法，以免造成页面无法回收。

### 5. Room:
功能介绍:   
作为Android官方的ORM数据库，与其他开源的ORM库一样都是在Sqlite基础上做进一步封装，目的是简化Sqlite操作数据库代码编写的复杂度，可以方便的对数据库进行创建、升级、CRUD表。   

组件导入：  
implementation 'androidx.room:room-runtime:2.2.2'   
annotationProcessor 'androidx.room:room-compiler:2.2.2'   

##### Room主要包含如下元素：
###### @Entity注解
定义一个模型类并在类上方添加@Entity注解，表示此类对应数据库中一张表，可在注解内设置tableName值即表的名字，不设置默认表名和类名相同，与@Entity配合使用的是修饰成员变量的@ColumnInfo
注解，表示表中的字段，注解内可设置name值表示字段名，同样不设置默认为成员变量名，设置typeAffinity值表示字段类型，此外还有@PrimaryKey注解表示此字段为主键，注解内的autoGenerate值表示自增，@NonNull注解表示此字段非空，@Ignore注解表示告诉Room忽略该字段或方法。
###### @Dao注解
定义一个接口并在接口上方添加@Dao注解，表示此接口为操作Entity表的Dao(Data Access Objects)对象，一个Entity表对应一个Dao对象，主要用于对表进行CRUD。Dao对象是在数据库初始化时通过数据
库声明类的抽象方法获得的。与@Dao注解配合使用的是修饰接口方法的@Insert、@Delete、@Update、@Query增删改查注解，@Query注解内要写上查询语句 eg @Query("SELECT * FROM 表名")，@Insert和
@Update注解内可设置onConflict值表示当出现冲突时的处理策略，默认为OnConflictStrategy.ABORT即终止并回滚事务，此外增删改方法需要声明表对象为参数。同时Room原生支持与LiveData组件配合，查询方法的返回值除了Entity修饰的表对象外，还可以为LiveData<List<Entrity修饰对象>>,这样ViewMode+LiveData 包装Dao对象查询数据库的结果后，当数据库数据发生变化不用再重复查询，页面直接能收到数据变化的通知。Excellent!!
###### @Database注解
自定义一个继承RoomDatabase的抽象类并在类上方添加@Database注解，表示此类为数据库对象，注解内可设置entities值用于声明数据库中包含哪些表，多个表可用{表类.class,表类.class}括起来，设置version值表示数据库版本，exportSchema值表示是否导出数据库信息 默认为true。   
数据库对象的实例化要通过Room对象获得，首先声明一个公有的获得数据库对象的方法，一般为单例模式，在方法内通过Room.databaseBuilder(applicationContext(),数据库对象.class, 数据库名字).build()获得数据库对象实例，在获得RoomDatabase.Builder对象后还可以追加一些额外的配置，eg .addMigrations(new Migration(数据库老版本号,数据库新版本号),策略)配置数据库升级策略 .fallbackToDestructiveMigration()配置升级异常时处理策略，代表没有对应版本间的升级方案时不让程序崩溃但是数据会丢失 即重新创建数据表 .createFromAsset("本地现有数据库.db")配置以现有数据库生成Room数据库 用于预填充数据。最后在类内部声明一个公有的抽象方法，用来返回操作表的Dao对象。然后就可以在ViewModel中先获得Database对象，再获得Dao对象，进而在子线程中通过Dao对象调用Dao中定义的操作数据库表的增删改查方法。    
@Database注解中exportSchema表示是否导出数据库信息，但是需要在gradle文件中指定输出路径，数据库生成修改成功就可以在对应目录下看到生成的 数据库版本号.json文件了。   
android{ defaultConfig{ javaCompileOptions{ annotationProcessorOptions{ arguments = ["room.schemaLocation":"model名字/schemas".toString()]}}}}

### 6. WorkManager:
功能介绍：   
WorkManager为了解决后台任务，并且是不需要立刻完成，当达到规定的条件时自动触发的 兼容范围非常广的异步任务处理组件，目的是控制后台任务以达到节省电量提升用户体验。不需要立即完成的任务比如日志上报、同步应用数据、静默下载更新包等。   
WorkManager根据当前设备的系统版本自动选择不同的执行方式，当设备系统版本>=23时采用JobScheduler完成任务，当系统版本<23时采用AlarmManager+Broadcast Receivers完成任务，但最终任务都是由Executor执行的。   

组件导入：implementation "androidx.work:work-runtime:2.2.0"   

用法：   
WorkManager使用方法先创建一个继承Worker的自定义类，表示即将要执行的任务，实现相关方法并在doWork方法内执行具体耗时任务，可以通过getInputData()接收外面传入进来的数据，并在任务执行完返回Result.failure() Result.success() 或者Result.retry()。   

然后通过new Constraints.Builder().build()创建一个任务触发条件的约束对象，Builder可以配置的条件有eg .setRequiresCharging(true)需要设备正在充电 .setRequiredNetworkType(NetworkType.CONNECTED)需要设备连接网络 .setRequiresBatteryNotLow(true)需要设备电量充足等。在创建一个任务请求对象new OneTimeWorkRequest.Builder (自定义任务类.class).build(),请求对象Builder也可配置一些参数 eg .setConstraints(约束对象)设置任务触发的约束条件 .setInitialDelay(duration, timeUnit)设置满足条件后任务触发的延迟时间 .addTag(tag)为任务设置标签 .setInputData(Data)为任务类传入数据等。   

最后通过WorkManager  .getInstance(this).enqueue(workRequest)将配置好的任务提交给系统。可以通过workManager.cancel...()方法取消任务，可以通过workManager.getWorkInfoByTagLiveData(tag).
.observe(this, new Observer<WorkInfo>() {...})方法观察任务状态 并可以在内部回调方法中通过workInfo获得Worker类回传回来的数据。   
PeriodicWorkRequest周期性任务和OneTimeWorkRequest一次性任务使用配置一样，但是周期间隔不能小于15分钟。    
当存在多个任务的情况下,可以通过workManager.beginWith(workRequestA).then(workRequestB).enqueue()将两个任务构建成一个任务链，begin会优先于then执行。
  
### 7. DataBinding:
功能介绍：  
DataBinding可以将数据模型直接声明到xml布局文件中，然后在UI控件上把模型中的属性直接赋值到控件的数据属性上，然后在页面Activity/Fragment中通过将具体的模型对象设置给布局文件，当页面中的模型对象数据变化时，布局上控件的数据显示也会自动变化，这就是DataBinding最主要的功能，此外它还支持双向绑定，即通过更改UI控件的显示，自动修改页面中模型的数据。   
DataBinding让布局承担了部分页面的功能，使xml布局和页面Activity之间更加解耦，减轻了页面的工作量。同时DataBinding也为能够更好的实现MVVM架构提供了基础。   

组件导入：不需要额外导入  

基本用法：  
首先在build.grdle中通过android{dataBinding{enabled = true}}配置开启DataBinding功能。  
然后创建普通的数据模型类，接着到布局文件中将布局根标签更改为layout,然后整个layout标签内可分为两部分，一部分为原本的布局构建代码放在一个大的布局标签内，一部分为数据代码放到data标签内，在data标签内可以通过variable标签声明一个变量，name代表变量名，type代表变量类型，可以为基本数据类型或是引用数据类型，如果引用数据类型是自定义类要写上类的全路径。data标签内还可以通过import标签导入一个自定义类，注意如果是用variable声明的代表此变量是一个对象，需要在Activity中设置对应的对象值进来，如果是import则代表引入的是一个类，可以在xml布局文件内直接调用此类的静态成员变量或方法。控件使用声明的数据时需要用@{}将java代码包裹起来，比如设置一个TextView控件的文本显示，则直接在text属性上写@{model对象.属性}。  
最后回到Activity页面中将原本的setContentView方法移除，用DataBindingUtil.setContentView(this,R.layout.xxx)方法代替，同步一下项目DataBinding会自动生成Activity类名倒过来并拼接上Binding字符的辅助类。用此类接收DataBindingUtil.setContentView()的方法作为返回值类型，通过该辅助类对象的set变量名方法将数据模型对象设置给布局文件，DataBinding基本用法变掌握完毕。  

嵌套布局：  
在布局中有include方式导入其他布局的嵌套情况时，可以通过在include标签中app:variable中变量名="@{变量名}"将变量传递给对应的布局中，同时在对应布局中需要声明同名同类型的变量接收。   

响应事件：  
DataBinding也支持在布局中响应事件，首先定义一个类，在类中定义一个共有的以View做为入参的方法，方法内即为控件上事件触发后要执行的操作。将自定义类在布局文件中以variable标签的形式引入，在控件的对应事件上比如点击事件eg android:onClick="@{引入的自定义对象名::自定义对象内的方法}"，最后在Activity页面中通过binding对象的set对象名方法将自定义类传进去，通过以上步骤，当布局上的控件被点击时，就会自动调用自定义类中的声明的方法。    

BindingAdapter:  
布局表达式中，我们通过@{对象.成员变量}的方式给控件的对应属性赋值，这样赋值之所以能有效的原因是DataBinding库为我们生成了大量的BindingAdapter类，几乎系统中常用UI控件它都为我们生成了绑定所需的BindAdapter类，通过查看对应UI控件的BindAdapter类源码，在控件可以改变的属性都有一个共有的静态方法与之对应，方法包含两个参数一个是UI控件一个是属性值，并在方法上方标注@BindingAdapter("要改变的属性")注解，所以DataBinding库是以静态方法的形式为UI控件的各个属性绑定了相应代码，当布局文件被渲染时，属性所绑定的方法会被自动调用。   
仿照DataBinding库生成的绑定类我们也可以自定义BindingAdapter为控件自定义新的属性，以ImageView控件为例为它新增一个app:image属性，我们先创建一个类并定义一个包含两个参数的setImage共有静态方法，第一个参数ImageView 代表imageview控件，第二个参数为String 代表传入的图片地址，在方法体内通过下载图片并将图片展示到控件上，即完成了image属性的功能.最后在方法上方加上@BindingAdapter ("image")注解，然后到布局中在ImageView控件加上app:image="@{声明的对象.imageUrl}"即可，最后别忘了在Activity中为对象赋值。同时BindingAdapter内支持方法重载，也可以在定义一个接收int类型加载本地资源的方法，布局中使用时传入int类型的资源id即可。BindingAdapter还支持多参数方法重载，可以在方法内定义两个接收数据参数，对应的也要在方法上面的注解中声明多个属性，value={"属性1","属性2"}表示多个属性，requireAll=false值表示是否同时需要必传。多参数重载还有一个用法为可选旧值，如果是可选旧值方法上方注解同一个接收数据参数一样，只是写上对应属性即可，只是方法的参数中第一个接收数据参数表示旧值即更改之前原先此属性显示的值，第二个参数表示新值即将要改变最新传入进来的值，如之前无旧值时旧值为类型的初值。       
     
双向绑定：  
DataBinding双向绑定有两种实现方式，第一种自定义一个类并继承BaseObservable 然后给要支持双向绑定的属性添加get/set方法，并在get方法上面添加@Bindable注解，在set方法中修改完属性值后添加notifyPropertyChanged(BR.属性)方法，接着在xml布局文件中布局表达式由原来的@{}变为@={},eg EditText控件的text属性设置 android:text ="@={variable声明的对象.属性}"，这样当修改EditText的值时会自动调用自定义类中的set方法，当修改内存中对象数据时布局上EditText显示也会跟着变化。    
第二中实现双向绑定的方式是使用ObservableField,这种实现方式更符合MVVM架构的设计。在ViewModel中用ObservableField<T>将要通信的对象包装起来即将普通对象包装成一个可观察的对象，然后通过new ObservableField<>()获得ObservableField对象，接着observableField.set(包装的对象)将ObservableField和包装的对象关联起来，最后在ViewModel中声明要实现双向绑定的属性的get/set方法并正常实现即可，其他布局和页面文件和第一种方式一样，observableField.get()方法可返回对象。    
第二种方式相比于第一种方式不需要继承类，不需要写@Bindable注解，不需要手动调用notifyPropertyChanged()方法,更加方便和简洁。   
      
RecyclerView中的用法：  
DataBinding也可用在RecyclerView的item布局中，修改item布局引入数据对象作为布局变量，并将变量的属性值作用于布局中的UI控件，在Adapter的onCreateViewHolder方法中通过DataBindingUtil
引入item布局并返回ViewDataBinding对象，用ViewDataBinding对象实例化ViewHolder对象，将ViewDataBinding对象声明为ViewHolder的成员变量以便给item布局设置数据，接着在onBindViewHolder方法中 根据position获取到指定对象数据后，通过holder.viewDataBinding对象将数据设置到布局中，即DataBinding在RecyclerView中的使用方式。

### 8. Paging:
功能介绍：  
正如它的名字一样，Paging组件的功能就是为了完成分页加载，并且为常见的几种分页机制提供了统一方案。   

组件导入：implementation 'androidx.paging:paging-runtime:2.1.0'  

##### Paging主要包含如下几个类：

###### PagedListAdapter
将RecyclerView的Adapter从原来的RecyclerView.Adapter改为继承PagedListAdapter<T, VH> 实现构造方法，并在类内容声明一个DiffUtil.ItemCallback对象用于计算两个数据列表之间的差异，其中要实现两个方法，第一个方法areItemsTheSame用于检测两个对象是否代表同一个item，eg 可以依据具体的业务规则比如item.id作为比较依据 oldItem.id.equals(newItem.id),第二个方法用来检测两个item内容是否一样areContentsTheSame 也可依据具体的业务规则或者直接oldItem.equals(newItem)。声明完DiffUtil.ItemCallback 则将它在构造方法中以super(diffCallback)形式返回给父类即可。同时在Activity中当获取到DataSource返回的数据后 通过PagedListAdapter的submitList方法将数据设置到adapter中。

###### PagedList
首先它是一个数据集合用来存放从DataSource中获取的数据，并且它可以通过配置通知DataSource提前多少条开始加载下一个数据、第一页加载多少数据，每页加载多少条数据等。通常将PagedList用
LiveData包装起来声明到ViewModel中，并在ViewModel中完成对LiveData<PagedList<模型对象>>的初始化，初始化需要先通过new PagedList.Config.Builder()... .build()方法实例化一个config对象并完成PagedList的相关配置，然后通过new LivePagedListBuilder<>(new  DataSource.Factory<Key, Value>(){...},config对象).build()完成初始化，以供外部使用并观察数据变化。PagedList的常用配置有config.stEnablePlaceholders是否开启item占位,config.setPageSize每页大小通常与DataSource中请求数据的参数保持一致,config.setPrefetchDistance距离底部多少条数据时开始加载下一页,config.setInitialLoadSizeHint首次加载数据的数据量PageSize的整倍数,config.setMaxSize PageList所能承受的最大数量.

###### DataSource
DataSource主要负责从网络或者room数据库中获取数据，执行具体的数据载入工作,并用来构建DataSource.Factory对象，即在DataSource.Factory的create方法中直接实例化一个DataSource对象 并返回即可。根据分页机制的不同Paging一共提供了三种DataSource。  

1).PositionalDataSource   
适用于在目标数据源数据量固定的情况下，且从任意位置开始往后取固定条数的分页机制。通常服务器需要两个参数start参数表示任意开始位置，count参数表示想后取count个数据。    
自定义一个类并继承PositionalDataSource<T>，实现loadInitial方法首次加载第一页调用的方法，在通过网络库将数据请求成功后通过callback.onResult(服务返回的数据集合,start数据起始位置，服务器总数据量)将数据返回给PagedList。实现loadRange方法加载下一页时调用的方法，通过网络库请求成功后通过callback.onResult(服务返回的数据集合)将数据返回给PagedList，用网络库请求下一页时的start起始位置参数直接从此方法的LoadRangeParams参数params.startPosition获取即可，Paging内部会自己维护。  
  
2).PageKeyedDataSource   
适用于以页的方式进行请求数据的分页机制。服务器需要两个参数page表示请求第几页，pageSize表示一页多少条数据。   
自定义一个类并继承PageKeyedDataSource<K,V>,实现loadInitial方法，数据请求成功后通过callback.onResult(服务返回的数据集合,previousPageKey,nextPageKey)将数据返回给PagedList，如没有前一页传null，下一页为当前请求的页码+1。实现loadAfter方法加载下一页调用该方法，请求的页码可从LoadParams参数params.key获得，在数据请求成功后通过callback.onResult(返回的数据集合,nextKey)将数据返回给PagedList。    

3).ItemKeyedDataSource   
适用于下一页数据需要依赖上一页数据最后一条数据的某个字段为key的分页机制。通常用于评论功能的实现，服务器需要两个参数since表示最后一个数据的key，pageSize表示向后加载多少条数据。  
自定义一个类并继承ItemKeyedDataSource<K,V>,实现loadInitial方法和loadAfter方法，并在网络库请求成功后通过callback.onResult(返回的数据集合)将数据返回给PagedList。还需要实现一个
getKey方法并根据具体业务，比如以数据id为key则返回 数据对象.id。

###### BoundaryCallback
适用于当应用程序需要缓存数据，程序数据源即来源于网络又来源于本地缓存的情况时，通过BoundaryCallback实现单一来源架构，以解决多数据源时效和新旧数据切换的问题，简化开发的复杂度。   

BoundaryCallback整体流程：    
首次加载页面如数据库有数据则直接将数据库数据返回给PageList进行渲染，如数据库为空首次加载数据会自动调用BoundaryCallback的onZeroItemsLoaded方法，在该方法内通过网络请求库加载数据，请求成功后将数据插入到room数据库中。当向上滑动页面加载下一页时，如数据库有下一页数据则直接将数据返回给PageList，如果数据库没有下一页数据会自动调用BoundaryCallback的onItemAtEndLoaded方法，方法内的参数为上一页最末尾的数据对象，在该方法内请求数据并在请求成功后将数据插入到room数据库。当向下滑动刷新数据时，首先手动清空数据库，由于数据库发生了变化数据库中没有数据了，PageList又会通知BoundaryCallback的onZeroItemsLoaded方法加载数据就回到了首次加载页面的状态。由于数据库是页面的唯一数据源，页面订阅了数据库的变化，所以当数据库中的数据发生变化时会直接反映到页面上。    

使用BoundaryCallback时由于将数据库作为页面的数据源，所以要先创建DataBase数据库类、Dao接口类、以及Entity数据表类，和其它情况下封装Dao接口的Query查询方法不同，由于和Paging联合使用所以将查询方法的返回值定义成DataSource.Factory<Integer,Entry表>，这是因为Room组件对Paging组件提供的原生的支持，Dao的其它注解方法和普通情况下一样。然后自定义一个继承UserBoundaryCallback<Entry表>的类，实现onZeroItemsLoaded和onItemAtEndLoaded方法，BoundaryCallback便定义完毕，最后在ViewModel中实例化LiveData<PagedList<Entry表>>对象时是通过：    
new LivePagedListBuilder<>(database.entry表dao().query对应方法,每页数据条数).setBoundaryCallback(new 自定义BoundaryCallback()).build()完成实例化对象，供外部使用和观察数据变化。也可以在ViewModel中添加清除数据库的方法，当在页面下拉刷新时清空数据库，触发BoundaryCallback重新加载数据并将数据通知给Activity，Adapter和Activity中的代码和其它三种分页机制一样。












