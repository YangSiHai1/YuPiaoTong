# 由 Dockerpacks 自动生成
# 本 Dockerfile 可能不能完全覆盖您的项目需求，若遇到问题请根据实际情况修改或询问客服

# 使用 Gradle 官方镜像
FROM gradle:7.6.1-jdk17

# 设置容器内的当前目录
WORKDIR /app

# 将包括源文件在内的所有文件拷贝到容器中
COPY . .
#COPY src /app/src

#添加本地的jar包到根目录
ADD YuPiaoTong.jar yupiaotong-wxcloud-1.0.jar

# 将pom.xml文件，拷贝到工作目录下
#COPY settings.gradle build.gradle /app/

# RUN chmod 777 /usr/bin/gradle

# 编译项目
RUN gradle clean assemble -x test --quiet

# 容器默认时区为UTC，如需使用上海时间请启用以下时区设置命令
RUN apk add tzdata && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo Asia/Shanghai > /etc/timezone

# 服务暴露的端口
EXPOSE 8080

# 运行项目
CMD ["java", "-jar", "/yupiaotong-wxcloud-1.0.jar"]