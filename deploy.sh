#!/bin/sh
set -e

which ssh-agent || (apk add --no-cache openssh-client)
eval $(ssh-agent -s)
echo "$SSH_PRIVATE_KEY" | tr -d '\r' | ssh-add - > /dev/null

mkdir -p ~/.ssh
chmod 700 ~/.ssh

apk add --no-cache gettext
envsubst < ./config.dist > ./config
envsubst < ./env/.api-gateway.env.dist > ./env/.api-gateway.env
envsubst < ./env/.user-service.env.dist > ./env/.user-service.env
envsubst < ./env/.photo-management-service.env.dist > ./env/.photo-management-service.env
envsubst < ./env/.post-service.env.dist > ./env/.post-service.env

cp config ~/.ssh

ssh -o "StrictHostKeyChecking=no" $SSH

ssh "$SSH" "sudo docker login -u gitlab-ci-token -p $CI_JOB_TOKEN $CI_REGISTRY"
ssh "$SSH" "sudo rm -rf wonderful-wander || echo 0"
ssh "$SSH" "git clone git@gitlab.com:wonderful-wander-team/wonderful-wander.git"
ssh "$SSH" "cd ~/wonderful-wander/ && git checkout $BRANCH"

scp ./env/.api-gateway.env "$SSH":~/wonderful-wander/env
scp ./env/.user-service.env "$SSH":~/wonderful-wander/env
scp ./env/.photo-management-service.env "$SSH":~/wonderful-wander/env
scp ./env/.post-service.env "$SSH":~/wonderful-wander/env

ssh "$SSH" "mv -f ~/wonderful-wander/nginx.conf ~/nginx/conf.d"
ssh "$SSH" "sudo docker pull registry.gitlab.com/wonderful-wander-team/wonderful-wander/ww-api-gateway"
ssh "$SSH" "sudo docker pull registry.gitlab.com/wonderful-wander-team/wonderful-wander/ww-user-service"
ssh "$SSH" "sudo docker pull registry.gitlab.com/wonderful-wander-team/wonderful-wander/ww-photo-management-service"
ssh "$SSH" "sudo docker-compose -f ~/wonderful-wander/docker-compose.yaml down"
ssh "$SSH" "sudo docker-compose -f ~/wonderful-wander/docker-compose.yaml up --no-build -d"
#ssh "$SSH" "sudo rm -r wonderful-wander/"
ssh "$SSH" "docker logout $CI_REGISTRY"
