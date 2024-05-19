package org.system.Controller;

import javafx.scene.input.MouseEvent;
import org.system.Model.Account;
import org.system.Model.Dependent;
import org.system.Model.PolicyHolder;
import org.system.utils.SceneController;

public class SharedVariable {
    public static Account loggedInAccount;
    public static PolicyHolder loggedInPolicyHolder;
    public static Dependent loggedInDependent;
    public static void resetValue() {
        SharedVariable.loggedInAccount = null;
        SharedVariable.loggedInPolicyHolder = null;
        SharedVariable.loggedInDependent = null;
    }

}