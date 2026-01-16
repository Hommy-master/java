FROM eclipse-temurin:17-jdk-focal

ENV HOME="/root" \
    DEBIAN_FRONTEND=noninteractive

# 更新包列表并安装常用的构建工具和实用程序
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    wget \
    curl \
    git \
    maven \
    gradle \
    unzip \
    zip \
    vim \
    && rm -rf /var/lib/apt/lists/*

# 设置工作目录
WORKDIR /app

# 复制应用程序源码（如果有的话）
# COPY . /app

# 编译和打包应用程序（示例，根据实际项目调整）
# RUN mvn clean package -DskipTests

# 暴露常见Java应用端口（如Spring Boot默认8080）
EXPOSE 8080

# 默认命令可运行Java应用
CMD ["/bin/sh", "-c", "sleep 10000"]