#!groovy

import jenkins.model.*
import hudson.util.*;
import jenkins.install.*;

Jenkins jenkins = Jenkins.getInstance()

jenkins.setInstallState(InstallState.INITIAL_SETUP_COMPLETED)
