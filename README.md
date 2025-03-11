# jenkins-shared-lib

* master server
================
* create a job
* Enter an item name: "test"
* choose which type: "pipeline"
* Search "pipeline" under pipeline script , in drop down select "Hello World" script will appear
* click on "apply and save"
* click on "console output", its a jenkins declarative language
* Install "blueocean" plugin
* go to "Manage Jenkins" , click on plugins, search "blueocean" (ExternalSite/Tool integration/ User interface)

step1:
======
get the code from git repo to jenkins file
===========================================
* In jenkins dashboard, 
* Enter an item name: "test"
* choose which type: "pipeline"
* Search "pipeline" under pipeline script , choose "pipeline script from SCM"
* choose "SCM" as Git
* Repositories
* ==============
* Repository URL:
https://github.com/devps23/expense-backend
* Branches To build:
------------------
*/main
* ScriptPath: by default : jenkins
* click on allpy and save
* now search config.xml file in /var/lib/jenkins
* cd /var/lib/jenkins --> jobs ---> test ---> config.xml
* copy this config.xml file and paste

WebHook:
========
* click on multibranch pipeline either frontend/backend , goto configure ----> Scan Multibranch Pipeline Triggerss
* instead of "build now " action for every node automatically executes based on webhooks
* install a plugin "Multibranch Scan Webhook Trigger"
* goto configure ----> Scan Multibranch Pipeline Triggers
* click "Scan by webhook"
* Trigger Token : expense-frontend
* click on ?(Question mark) ; trigger token
* copy that token like jenkins_url....?token=expense-backend
* paste this trigger token into a webhook in github
  copy the config.xml file and paste under templates
  for tags need to click "build now " where as for branch automatically build now action will happened
* node server
==============
* in node server install java17 version 
* for example if a server is a 'ci-server' then should install java17 version, then only agent will connect to jenkins server.


aws-auth:
=========
* install aws-auth in workstation
* aws-auth is used to provide authentication from eks cluster to required node agent.
* 
sh 'argocd app set ${component} --parameter appVersion=${TAG_NAME}'
the above cmd is used to set parameter based on tagname, but this parameter updates on only argocd dashboard
sh 'argocd app sync ${component}'
once when we sync then only tagname version changed inside a container.

$ aws-auth upsert --maproles --rolearn arn:aws:iam::041445559784:instance-profile/workstattion_role --username system:node:{{EC2PrivateDNSName}} --groups system:bootstrappers system:nodes
