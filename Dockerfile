
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 将 build.gradle 和其他必需文件拷贝到镜像中
COPY build.gradle /app
COPY src /app/src

# 安装 Gradle 并编译项目
RUN apt-get update && apt-get install -y wget unzip \
    && wget https://services.gradle.org/distributions/gradle-7.0-bin.zip \
    && unzip gradle-7.0-bin.zip \
    && mv gradle-7.0 /usr/share/gradle \
    && rm -rf gradle-7.0-bin.zip \
    && export PATH=$PATH:/usr/share/gradle/bin \
    && gradle build --no-daemon \
    && rm -rf /var/lib/apt/lists/* \
    && rm -rf /app/src \
    && mv /app/build/libs/*.jar /app/app.jar

EXPOSE 80

# 运行应用程序
ENTRYPOINT ["java", "-jar", "/app/app.jar"]



## 由 Dockerpacks 自动生成
## 本 Dockerfile 可能不能完全覆盖您的项目需求，若遇到问题请根据实际情况修改或询问客服
#
## 使用 Gradle 官方镜像
## gradle:7.6.1-jdk17 AS build —— openjdk:17-jdk-slim
#FROM gradle:7.6.1-jdk17 AS build
#
## 复制构建脚本
#COPY build.gradle .
#COPY settings.gradle .
#
## 下载Gradle依赖
#RUN gradle build --no-daemon
#
## 复制源文件
#COPY src/ ./src/
#
## 编译并打包应用程序
#RUN gradle build --no-daemon
#
#
#
## 生成最终的Docker镜像
#FROM openjdk:17-jdk-slim
#
## 设置容器内的当前目录
#WORKDIR /app
#
## 复制应用程序jar包
#COPY --from=build /app/build/libs/*.jar ./app.jar
#
#EXPOSE 80
#
## 运行项目
#CMD ["java", "-jar", "/app/app.jar"]