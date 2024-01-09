# Define base image with required Java version
FROM bellsoft/liberica-openjdk-centos:17

# Set working directory in the container
WORKDIR /app/iq

# Copy the JAR file into the container
COPY rest-controller/target/InterviewQuiz.jar /app/iq/InterviewQuiz.jar

# Expose the port of the service
EXPOSE 8080

# Command to run application when the container starts
CMD ["java", "-jar", "./InterviewQuiz.jar"]
