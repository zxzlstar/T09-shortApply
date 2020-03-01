package com.star.shop.basic.service;

import com.star.shop.basic.entity.Menu;
import com.star.shop.basic.entity.User;
import com.star.shop.basic.repository.*;
import com.star.shop.basic.shiro.ShiroUtils;
import com.star.shop.basic.utils.Constants;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 
 * 
 * <p>Title:MenuService</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月17日
 */
@Service
public class MenuService extends BaseService<Menu, String>{
	private @Resource
	MenuRepository menuRepository ;
	
	private @Resource
	UserRepository userRepository ;
	
	private @Resource
	PermissionRepository permissionRepository ;
	
	private @Resource
	UserRoleRepository userRoleRepository ;
	
	@Override
	protected BaseRepository<Menu, String> getBaseRepository() {
		return this.menuRepository;
	}

	public ResultVo listMenu(){
		List<Menu> list =  this.menuRepository.findAll() ;
		return ResultVo.s(list) ;
	}
	
	public ResultVo treeMenu(){
		User user = this.userRepository.findById(ShiroUtils.getUserId()).get() ;
		List<Menu> list = null ;
		if(Constants.DEFAULT_ADMIN_NAME.equals(user.getUsername())){
			list=  this.menuRepository.findByType(0) ;
			for(Menu menu : list){
				menu.setList(this.menuRepository.findByPid(menu.getId()));
			}
		}else{
			List<String> roleIdList = userRoleRepository.findRoleIdByUserId(user.getId()) ;
			List<String> menuIdList = permissionRepository.findMenuIdByRoleIdIn(roleIdList) ;
			
			list=  this.menuRepository.findByTypeAndIdIn(0, menuIdList) ;
			for(Menu menu : list){
				menu.setList(this.menuRepository.findByPidAndIdIn(menu.getId() , menuIdList));
			}
		}
		
		
		
		return ResultVo.s(list) ;
	}
	
	public ResultVo saveMenu(Menu menu){
		this.menuRepository.save(menu) ;
		menu.setPath(this.getPath(menu));
		this.menuRepository.save(menu) ;
		return ResultVo.s() ;
	}
	
	public ResultVo infoMenu(String id){
		Menu menu = this.menuRepository.findById(id).get() ;
		if(menu == null){
			return ResultVo.e(400, "没有查询到数据") ;
		}else{
			return ResultVo.s(menu) ;
		}
	}
	
	public ResultVo modifyMenu(Menu menu){
		String path = this.getPath(menu) ;
		menu.setPath(path);
		this.menuRepository.save(menu) ;
		//更新下级
		this.updateChildrenPath(menu);
		
		return ResultVo.s() ;
	}
	
	public ResultVo removeMenu(String[] ids){
		for(String id : ids){
			if(this.menuRepository.findByPid(id).size() > 0){
				return ResultVo.e(400, "存在子菜单不能删除");
			}
			
			this.menuRepository.deleteById(id); ;
			this.permissionRepository.deleteByMenuId(id) ;//删除菜单的权限
		}
		
		return ResultVo.s() ;
	}
	
//	public String getPath(Menu menu){
//		String path = menu.getId() ;
//		if(StringUtils.hasText(menu.getPid())){
//			path = this.getPath(this.menuRepository.findOne(menu.getPid())) + "," + path ;
//		}
//		
//		return path ;
//	}
	
	@Override
	public List<Menu> findByPid(String pid) {
		return this.menuRepository.findByPid(pid) ;
	}

}
