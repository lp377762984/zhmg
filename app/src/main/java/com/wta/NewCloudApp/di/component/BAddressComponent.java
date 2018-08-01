package com.wta.NewCloudApp.di.component;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.di.module.BAddressModule;
import com.wta.NewCloudApp.mvp.ui.widget.link_with4_class.AddressSelector;

import dagger.Component;

@ActivityScope
@Component(modules = BAddressModule.class, dependencies = AppComponent.class)
public interface BAddressComponent {
    void inject(AddressSelector selector);
}
