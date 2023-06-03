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
#COPY YuPiaoTong.jar yupiaotong-wxcloud-1.0.jar

# 将pom.xml文件，拷贝到工作目录下
#COPY settings.gradle build.gradle /app/

# RUN chmod 777 /usr/bin/gradle

#
## 配置环境变量和jdk的安装目录
#ENV JAVA_DIR=/usr/local
## 安装jdk
## \ 就是换到下一行输入，对实际命令没有任何影响，只是为了方便观看
#RUN cd $JAVA_DIR \
#    # 使用tar命令将当前目录下（这里因为上面配置了所以所在的目录也就是 /usr/local/下面）的jdk文件进行解压
#    && tar -xvf ./jdk-8u321-linux-x64.tar.gz \
#    # 然后修改解压后的文件名为java8 ，此时就是 /usr/local/java8
#    # 这里需要注意的是下面的 jdk1.8.0_321，他就是解压后的文件名
#    # 如果这里你不清楚解压后的文件是什么，则可以先解压看以下文件名是什么，然后在进行书写这里的名称
#    && mv ./jdk1.8.0_321 ./java8
## 配置JAVA的环境变量
#ENV JAVA_HOME=$JAVA_DIR/java8
## 配置到PAHT中
#ENV PATH=$PATH:$JAVA_HOME/bin

# 编译项目
#RUN gradle clean assemble -x test --quiet

# 容器默认时区为UTC，如需使用上海时间请启用以下时区设置命令
#RUN apk add tzdata && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo Asia/Shanghai > /etc/timezone

# 服务暴露的端口
EXPOSE 8080

# 运行项目
CMD ["java", "-jar", "YuPiaoTong.jar"]