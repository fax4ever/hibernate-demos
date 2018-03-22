#!/usr/bin/env bash
cp -f ../config/nginx.conf ./message-board-web

#TODO: add here build of app

oc start-build message-board-web --from-dir=./message-board-web --follow