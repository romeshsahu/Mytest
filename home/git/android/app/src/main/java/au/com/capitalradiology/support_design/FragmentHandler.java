package au.com.capitalradiology.support_design;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import au.com.capitalradiology.R;


public class FragmentHandler {

	public void displayFragment(Fragment fragment,FragmentManager fragmentManager,String fragTag,Bundle bundle,boolean addToBackStack) {
		
		FragmentTransaction transaction = fragmentManager
				.beginTransaction();
		
		transaction.replace(R.id.fragment_container1, fragment);
		
		if(addToBackStack)
			transaction.addToBackStack(fragTag);
		else
			transaction.addToBackStack(null);
		if(bundle!=null)
			fragment.setArguments(bundle);
		
		//setCurrentFragmentId(which);

		// Commit the transaction
		transaction.commit();

	}
	
	/*Fragment frag = null;
	switch (which) {
	case 1:
		frag = homeFragment;
		break;
		
	case 2:
		frag = loginFragMent;
		break;
		
	case 3:
		//frag = new SettingsFragment();
		break;

	default:
		break;
	}*/
	
}
