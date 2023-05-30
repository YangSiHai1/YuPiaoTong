# 由 Dockerpacks 自动生成
# 本 Dockerfile 可能不能完全覆盖您的项目需求，若遇到问题请根据实际情况修改或询问客服

# 使用 Gradle 官方镜像
FROM gradle:6-jdk11

# 设置容器内的当前目录
WORKDIR /app

# 将包括源文件在内的所有文件拷贝到容器中
COPY . .

# 编译项目
RUN gradle clean assemble -x test --quiet

# 服务暴露的端口
EXPOSE 8080

# 运行项目
CMD ["java", "-jar", "gradle/wrapper/gradle-wrapper.jar"]