

# Proguard是一个Java类文件压缩器、优化器、混淆器、预校验器。压缩环节会检测以及移除没有用到的类、字段、方法以及属性。

# 混淆后默认会在工程目录app/build/outputs/mapping/release（debug）下生成一个mapping.txt文件，这就是混淆规则

-dontshrink #关闭压缩
#压缩(Shrinking)：默认开启，用以减小应用体积，移除未被使用的类和成员，并且会在优化动作执行之后再次执行（因为优化后可能会再次暴露一些未被使用的类和成员）。
#keep 这个关键字会阻止这个类在未引用的情况下，被删除。而keepname  不会
#-dontshrink  可以使这个（ shrinkResources true  ）不起作用

-dontoptimize  #关闭优化  优化是默认情况下启用的;。所有方法都在字节码级进行优化。
-optimizationpasses n  #表示proguard对代码进行迭代优化的次数，Android一般为5
#优化（Optimization）：默认开启，在字节码级别执行优化，让应用运行的更快。 （上面这两个一般只使用一个）

-dontobfuscate  #关闭混淆
#混淆（Obfuscation）：默认开启，增大反编译难度，类和类成员会被随机命名，除非用keep保护。

-dontpreverify
#不进行预校验，预校验是作用在Java平台上的，Android平台上不需要这项功能，去掉之后还可以加快混淆速度

-verbose
#打印混淆的详细信息

-keepattributes *Annotation*
#保留注解参数

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#混淆时采用的算法

-useuniqueclassmembernames
#把混淆类中的方法名也混淆了

-renamesourcefileattribute SourceFile
#将文件来源重命名为“SourceFile”字符串

-keepattributes SourceFile,LineNumberTable
#保留行号

-keepattributes Signature
#保持泛型

#具体规则
#（保持类名不混淆）
-keep class com.thc.test.*
#一颗星表示只是保持该包下的类名，而子包下的类名还是会被混淆；
-keep class com.thc.test.**
#两颗星表示把本包和所含子包下的类名都保持；

#（保持整个类不混淆）
-keep class com.thc.test.*{*;}
#既可以保持该包下的类名，又可以保持类里面的内容不被混淆;
-keep class com.thc.test.**{*;}
#既可以保持该包及子包下的类名，又可以保持类里面的内容不被混淆;

#{
    -keepnames class_specification
    #[-keep allowshrinking class_specification 的简写]
    #指定要保留名称的类和类成员，前提是在压缩阶段未被删除。仅用于模糊处理(就是在压缩阶段，如果这个类没有被引用的话，那么这个类会被删去，所以这个没用)
#}


#{
    #类
    -keep class com.xlpay.sqlite.cache.BaseDaoImpl #（保持某个类名不被混淆（但是内部内容会被混淆））
    -keep class com.xlpay.sqlite.cache.ProguardTest$MyClass{*;} #要保留一个类中的内部类不被混淆需要用 $ 符号
    -keep class com.xlpay.sqlite.cache.BaseDaoImpl{*;} #（保持某个类的 类名及内部的所有内容不会混淆））

        -keep class com.thc.gradlestudy.MyProguardBean{
              <init>; #匹配所有构造器
              <fields>;#匹配所有域
              <methods>;#匹配所有方法
              }
        -keep class com.xlpay.sqlite.cache.BaseDaoImpl{
              public <methods>;#保持该类下所有的共有方法不被混淆
              public *;#保持该类下所有的共有内容不被混淆
              private <methods>;#保持该类下所有的私有方法不被混淆
              private *;#保持该类下所有的私有内容不被混淆
              public <init>(java.lang.String);#保持该类的String类型的构造方法
          }

#}

#{
    #派生  使用Java的基本规则来保护特定类不被混淆，比如用extends，implement等这些Java规则，如下：保持Android底层组件和类不要混淆

    -keep public class * extends android.app.Activity
    -keep public class * extends android.app.Application
    -keep public class * extends android.app.Service
    -keep public class * extends android.content.BroadcastReceiver
    -keep public class * extends android.content.ContentProvider
    -keep public class * extends android.view.View

    -keep class * implements name.huihui.example.TestInterface { *; }

#}

#保持ProguardTest类下test(String)方法不被混淆
#保留类和类中的成员，防止被混淆或移除，保留指明的成员
    -keepclassmembernames class com.xlpay.sqlite.cache.ProguardTest{
      public void test(java.lang.String);
  }
#指定保留的类和类成员，条件是所指定的类成员都存在（既在压缩阶段没有被删除的成员，效果和keep差不多）
  -keepclasseswithmembernames class com.xlpay.sqlite.cache.ProguardTest





#保持native方法不被混淆   jni方法不可混淆，因为native方法是要完整的包名类名方法名来定义的，不能修改，否则找不到；
-keepclasseswithmembernames class * {
  native <methods>;
}
#Parcelable的子类和Creator静态成员变量不混淆，否则会产生Android.os.BadParcelableException异常；
-keep class * implements Android.os.Parcelable {
  # 保持Parcelable不被混淆
  public static final Android.os.Parcelable$Creator *;
}
#保持所有实现 Serializable 接口的类成员
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#使用enum类型时需要注意避免以下两个方法混淆，因为enum类的特殊性，以下两个方法会被反射调用，见第二条规则。
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}



#1。反射用到的类混淆时需要注意：只要保持反射用到的类名和方法即可，并不需要将整个被反射到的类都进行保持
#2。与服务端交互时，使用GSON、fastjson等框架解析服务端数据时，所写的JSON对象类不混淆，否则无法将JSON解析成对应的对象；
#3。使用第三方开源库或者引用其他第三方的SDK包时，如果有特别要求，也需要在混淆文件中加入对应的混淆规则；
#4。有用到WebView的JS调用也需要保证写的接口方法不混淆，原因和第一条一样；


#Android Lint 是有 Android SDK 提供的一种静态代码检测工具
#在 Android Studio 创建的 Android 项目中运行 ./gradlew lint 可以获得 Lint 检测结果


#gradle
#android{
#    buildTypes {
#
#            release {
#                //buildConfigField "boolean", "USE_CANARY", "false"  第一个：确定值的类型，第二个：指定key的名字，第三个：传值
#
#                //buildConfigField "String", "SERVER_HOST", "\"200.200.200.50/\""
#                ////取值
#                //String host = BuildConfig.SERVER_HOST;


#                buildConfigField "boolean", "LOG_DEBUG", "false"
#                buildConfigField "boolean", "USE_CANARY", "false"
#                minifyEnabled true   //打开混淆
#                shrinkResources true  //用shrinkResources true开启资源压缩后，所有未被使用的资源默认被移除。
#                zipAlignEnabled true  //zipAlign可以让安装包中的资源按4字节对齐，这样可以减少应用在运行时的内存消耗
#                proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
#            }
#        }
#    lintOptions {
#            // true--关闭lint报告的分析进度
#            quiet true
#            // true--错误发生后停止gradle构建
#            abortOnError false
#            // true--只报告error
#            ignoreWarnings true
#            // true--忽略有错误的文件的全/绝对路径(默认是true)
#            //absolutePaths true
#            // true--检查所有问题点，包含其他默认关闭项
#            checkAllWarnings true
#            // true--所有warning当做error
#            warningsAsErrors true
#            // 关闭指定问题检查
#            disable 'TypographyFractions', 'TypographyQuotes'
#            // 打开指定问题检查
#            enable 'RtlHardcoded', 'RtlCompat', 'RtlEnabled'
#            // 仅检查指定问题
#            check 'NewApi', 'InlinedApi'
#            // true--error输出文件不包含源码行号
#            noLines true
#            // true--显示错误的所有发生位置，不截取
#            showAll true
#            // 回退lint设置(默认规则)
#            lintConfig file("default-lint.xml")
#            // true--生成txt格式报告(默认false)
#            textReport true
#            // 重定向输出；可以是文件或'stdout'
#            textOutput 'stdout'
#            // true--生成XML格式报告
#            xmlReport false
#            // 指定xml报告文档(默认lint-results.xml)
#            xmlOutput file("lint-report.xml")
#            // true--生成HTML报告(带问题解释，源码位置，等)
#            htmlReport true
#            // html报告可选路径(构建器默认是lint-results.html )
#            htmlOutput file("lint-report.html")
#            //  true--所有正式版构建执行规则生成崩溃的lint检查，如果有崩溃问题将停止构建
#            checkReleaseBuilds true
#            // 在发布版本编译时检查(即使不包含lint目标)，指定问题的规则生成崩溃
#            fatal 'NewApi', 'InlineApi'
#            // 指定问题的规则生成错误
#            error 'Wakelock', 'TextViewEdits'
#            // 指定问题的规则生成警告
#            warning 'ResourceAsColor'
#            // 忽略指定问题的规则(同关闭检查)
#            ignore 'TypographyQuotes'
#        }


#}





