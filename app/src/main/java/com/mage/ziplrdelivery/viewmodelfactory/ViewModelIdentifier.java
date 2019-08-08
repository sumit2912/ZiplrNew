package com.mage.ziplrdelivery.viewmodelfactory;

import com.mage.ziplrdelivery.screen.Screen;
import com.mage.ziplrdelivery.viewmodelfactory.viewmodel.DashBoardViewModel;
import com.mage.ziplrdelivery.viewmodelfactory.viewmodel.MobileNoViewModel;
import com.mage.ziplrdelivery.viewmodelfactory.viewmodel.PasswordViewModel;
import com.mage.ziplrdelivery.viewmodelfactory.viewmodel.RegistrationViewModel;

public class ViewModelIdentifier {
    // All ViewModel unique key type here
    public static final String KEY_REGISTRATION_VIEW_MODEL = RegistrationViewModel.class.getSimpleName();
    public static final String KEY_MOBILE_NO_VIEW_MODEL = MobileNoViewModel.class.getSimpleName();
    public static final String KEY_PASSWORD_VIEW_MODEL = PasswordViewModel.class.getSimpleName();
    public static final String KEY_DASH_BOARD_VIEW_MODEL = DashBoardViewModel.class.getSimpleName();

    // All ViewModel destroyer activity name type here
    public static final String DES_REGISTRATION_VIEW_MODEL = Screen.REGISTRATION_ACTIVITY;
    public static final String DES_MOBILE_NO_VIEW_MODEL = Screen.PASSWORD_ACTIVITY;
    public static final String DES_PASSWORD_VIEW_MODEL = Screen.PASSWORD_ACTIVITY;
    public static final String DES_DASH_BOARD_VIEW_MODEL = Screen.DASH_BOARD_ACTIVITY;
}
