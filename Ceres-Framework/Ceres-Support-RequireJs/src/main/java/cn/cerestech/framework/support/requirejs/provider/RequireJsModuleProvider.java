package cn.cerestech.framework.support.requirejs.provider;

import java.util.List;
import java.util.function.Supplier;

import cn.cerestech.framework.support.requirejs.entity.RequireJsModule;

public interface RequireJsModuleProvider extends Supplier<List<RequireJsModule>> {

}
