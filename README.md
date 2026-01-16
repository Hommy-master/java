# Java 开发环境 Docker 镜像

这是一个预配置的 Java 开发环境，包含了 Java、Maven、Gradle 等开发工具。

## 特性

- OpenJDK 17
- Maven
- Gradle
- Git
- 常用命令行工具 (wget, curl, vim, zip, unzip)

## 使用方法

### 构建镜像

```bash
docker-compose build
```

### 运行容器

```bash
docker-compose up -d
```

### 进入容器

```bash
docker exec -it java bash
```

### 在容器内运行 Java 应用

```bash
# 编译 Java 项目
mvn clean package

# 运行 JAR 包
java -jar your-application.jar
```

### 运行单个Java文件（例如coze.java）

1. 将coze.java文件放在项目根目录
2. 构建并运行容器：

```bash
# 构建镜像（包含编译coze.java）
docker-compose build

# 运行容器（这会自动编译并运行coze.java）
docker-compose up -d
```

或者，如果您只是想临时运行一个Java文件而不修改Dockerfile：

```bash
# 进入容器
docker exec -it java bash

# 手动编译和运行Java文件
cd /app
cp /root/workspace/coze.java .  # 如果文件在workspace目录下
javac coze.java
java coze
```

## 端口映射

- 主机 8080 端口映射到容器 8080 端口

## 卷挂载

- `./workspace` 映射到容器 `/root/workspace`
- `~/.m2` 映射到容器 `/root/.m2` (Maven 本地仓库)

⭐ 如果你觉得这个项目对你有帮助，欢迎点个 Star 支持一下！你的支持是我持续维护和改进项目的最大动力 😊