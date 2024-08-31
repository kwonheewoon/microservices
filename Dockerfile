FROM openjdk:21 as builder

WORKDIR extracted
ADD ./build/libs/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract
RUN ls -R . # 파일 구조를 확인하기 위해 추가

FROM openjdk:21
WORKDIR application

ENV SPRING_PROFILES_ACTIVE=docker
ENV SERVER_PORT=8080

COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]

# docker build -t product-service .
# docker run -d -p 7001:7001 --name product-service product-service