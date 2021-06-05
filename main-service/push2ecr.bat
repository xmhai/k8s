docker build -t mainservice .
docker tag mainservice:latest 696823325773.dkr.ecr.ap-southeast-1.amazonaws.com/mainservice:latest
docker push 696823325773.dkr.ecr.ap-southeast-1.amazonaws.com/mainservice:latest
