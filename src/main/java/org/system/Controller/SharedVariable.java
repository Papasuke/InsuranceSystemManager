package org.system.Controller;

import org.system.Model.Account;
import org.system.Model.PolicyHolder;

public class SharedVariable {
    public static boolean openOnce = false;
    public static Account loggedInAccount;
    public static PolicyHolder loggedInPolicyHolder;
}