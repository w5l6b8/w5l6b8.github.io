# 问题和解答




>## Unity


### Unity3D中C#无法调用Unity引擎中的类

在unity打开Edit编辑栏，点击Preference,选择External Tools工具栏，把External scipt 那里改为你安装的visual studio版本



>## java

### 程序打成jar包时，如何获取到jar目录所在的根路径

    String rootPath = System.getProperty("user.dir");

### List 转数组
    
    String[] stringArray = myList.stream().toArray(String[]::new);

### 解决idea编译时遇到write classing卡住

在我编译项目时偶尔会遇到write classing卡住，以下是解决方法
idea->settings->build,execution,deployment->compiler
把build process heap size 改大一点比如2048


>## windows

### CUDA 示例构建时错误 - Windows 10 和 Cuda 10.1 Toolikt

    C:\Program Files\NVIDIA GPUComputing Toolkit\CUDA\v10.1\extras\visual_studio_integration\MSBuildExtensions
    复制 MSBuildExtensions 文件夹中的所有内容。

    C:\Program Files (x86)\Microsoft Visual Studio\2019\Professional\MSBuild\Microsoft\VC\v160\BuildCustomizations
    将其粘贴到 BuildCustomizations 文件夹中