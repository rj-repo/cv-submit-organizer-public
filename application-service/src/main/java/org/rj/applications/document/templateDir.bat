@echo off
REM Script to create template package folder structure

REM Create 'application' folder and subfolders
mkdir application
mkdir application\usecase
mkdir application\rest

REM Create 'domain' folder and subfolders
mkdir domain
mkdir domain\model
mkdir domain\ports
mkdir domain\ports\in
mkdir domain\ports\out

REM Create 'infrastructure' folder and subfolders
mkdir infrastructure
mkdir infrastructure\persistence
mkdir infrastructure\persistence\entity

echo Template package structure created successfully.