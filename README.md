针对<<1972年国际海上避碰规则>>( 以下简称 "避碰规则" ), 实现了三种搜索功能: 

- 语义搜索. 基于句子相似性进行搜索, 综合考虑了语义及句法结构的相似性.
- 智能问答. 从避碰规则中抽取三元组, 基于句子的依存语法, 通过匹配三元组搜索答案.
- 规则推理. 基于句子相似性进行推理.

# 用法

## 例子

`/CollisionRegulation1972/src/spart/main/Main.java`:

```java
final InputStream is = Main.class.getResourceAsStream("/1972年国际海上避碰规则.txt");
SearchEngine engine = SearchEngine.initiate(is);

{
    final SearchResult result = engine.semanticSearch("用机器推动的所有船只");
    System.out.println(result.first(3)); 
    // [“机动船”一词，指用机器推进的任何船舶。 - 0.7651329, 号笛的安置 - 0.74486685, 对备有可使用的雷达的船舶，还应考虑： - 0.7390274]
}

{
    final SearchResult result = engine.smartQA("船舶包括哪些");
    System.out.println(result);
    // [船筏 - -1.0, 水上飞机 - -1.0]
}		

{
    final SearchResult result = engine.regulationInfer("两船的不同船舷受风");
    System.out.println(result);
    // [左舷受风的船应给他船让路 - 0.9421429]
}

{
    final SearchResult result = engine.regulationInfer("两船交叉相遇, 有碰撞危险, 他船在本船右舷");
    System.out.println(result);
    // [应尽可能及早地采取大幅度的行动 - 1.0, 船舶应给他船让路 - 0.9619129]
}

SearchEngine.exit();
```

## 环境

JDK 1.8

Python 3.8

主要代码使用 Java, 基于 jep 与 Python 进行交互 (参见 `/CollisionRegulation1972/src/spart/py/PyTool.java` ) .

## 依赖

`/CollisionRegulation1972/pom.xml`:

jep : 4.1.1
hanlp : portable-1.8.4
jna : 4.5.1
fastison : 2.0.6

本地库 `/CollisionRegulation1972/bin/win32-x86-64`:

NLPIR.dll (NLPIR 的本地库)

## 配置

1 [HanLP](https://github.com/hankcs/HanLP) 需要配置根目录, 以下两种方式均可 (把"./data-for-1.7.5"更改为你的实际目录):

- `/CollisionRegulation1972/src/hanlp.properties`:

  修改 `root=./data-for-1.7.5` 

- Java 中使用: 

  ```java
  System.setProperty("HANLP_ROOT", "/data-for-1.7.5");
  ```

2 首次使用[OpenHowNet](https://github.com/thunlp/OpenHowNet), 需要执行:

```Python
OpenHowNet.download()
```

3 将 [dsnf](https://github.com/Megre/dsnf) 拷贝到 `D:/python38/Lib` (把`D:/python38`更改为你的Python环境的根目录)

4 [NLPIR](https://github.com/NLPIR-team/NLPIR) 首次使用需要授权. 授权方式是将授权文件拷贝到指定目录. 授权文件在[链接](https://github.com/NLPIR-team/NLPIR)中可以找到, 具体操作参见[官网](https://github.com/NLPIR-team/NLPIR).

# 引用

- [OpenHowNet](https://github.com/thunlp/OpenHowNet) (Python) 词语相似度的计算
- [open-entity-relation-extraction](https://github.com/lemonhu/open-entity-relation-extraction) (Python) 三元组抽取 (DSNF). 为了能在Python 3.8中方便的使用, 对此项目进行了更改, 参见 [dsnf](https://github.com/Megre/dsnf).
- [NLPIR](https://github.com/NLPIR-team/NLPIR) (Java本地库) 中科院分词系统
- [HanLP](https://github.com/hankcs/HanLP) (Java) 依存句法分析

