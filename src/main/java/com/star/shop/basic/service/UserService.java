package com.star.shop.basic.service;

import com.star.shop.basic.entity.User;
import com.star.shop.basic.entity.UserRole;
import com.star.shop.basic.repository.*;
import com.star.shop.basic.shiro.ShiroUtils;
import com.star.shop.basic.utils.Constants;
import com.star.shop.basic.utils.MD5Utils;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;


/**
 * 
 * 
 * <p>
 * Title:UserService
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Company:
 * </p>
 *
 * @author x.zhang
 *
 * @date 2018年8月17日
 */
@Service
public class UserService extends BaseService<User, String> {
	private @Resource
	UserRepository userRepository;
	
	private @Resource
	UserRoleRepository userRoleRepository ;
	
	private @Resource
	PermissionRepository permissionRepository ;
	
	private @Resource
	MenuRepository menuRepository ;

	@Override
	protected BaseRepository<User, String> getBaseRepository() {
		return this.userRepository;
	}
	
	
	public Set<String> listUserRoles(String userid){
		Set<String> roles = new HashSet<>(this.userRoleRepository.findRoleIdByUserId(userid)) ;
		return roles ;
	}
	
	public Set<String> listUserPerms(String userid){
		Set<String> data = Collections.emptySet()  ;
		User user = this.userRepository.findById(userid).get() ;
		if(user.getUsername().equals(Constants.DEFAULT_ADMIN_NAME)){
			//管理员
			data = this.menuRepository.findAllPerms() ;
		}else{
			List<String> roleIdList = this.userRoleRepository.findRoleIdByUserId(userid) ; //先查询所有角色ID
			if(roleIdList.size() > 0){
				List<String> menuIdList = this.permissionRepository.findMenuIdByRoleIdIn(roleIdList) ;
				if(menuIdList.size() > 0){
					data = this.menuRepository.findByIdIn(menuIdList) ;
				}
				
			}
			
			
		}
		
		return data ;
	}
	
	public User getByUserName(String username){
		return this.userRepository.findByUsername(username) ;
	}
	
	public ResultVo listUser(Map<String , Object> params){
		int pageSize = Integer.parseInt(params.get("pageSize").toString()) ;
		int pageNumber = Integer.parseInt(params.get("pageNumber").toString()) ;
		
		PageRequest pageable = new PageRequest(pageNumber-1 ,pageSize, Direction.DESC, "ctime") ;
		
		Specification<User> spec = new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root,CriteriaQuery <?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<>();
				if(!StringUtils.isEmpty(params.get("username"))){
					predicate.add(cb.like(root.get("username"),"%"+ params.get("username")+"%")) ;
				}
				
                Predicate[] pre = new Predicate[predicate.size()];
                query = query.where(predicate.toArray(pre)) ;
                return query.getRestriction() ;
			}
		};
		
		Page<User> page = this.userRepository.findAll(spec, pageable) ;
		
		ResultVo resultVo = ResultVo.s(page.getContent()) ;
		resultVo.setTotal(page.getTotalElements());
		return resultVo ;
	}

	
	public ResultVo infoUser(String id){
		User user = null ;
		if(StringUtils.isEmpty(id)){
			user = userRepository.findById(ShiroUtils.getUserId()).get() ;
		}else{
			user = userRepository.findById(id).get() ;
			
		}
		if(user == null){
			return ResultVo.e(400, "没有查询到数据") ;
		}
		user.setRoleIdList(this.userRoleRepository.findRoleIdByUserId(user.getId()));
		return ResultVo.s(user) ;
	}


	public ResultVo modifypassword(String oldPassword, String newPassword) {
		User user =  this.userRepository.findById(ShiroUtils.getUserId()).get() ;
		
		if(!MD5Utils.encode(oldPassword).equals(user.getPassword())){
			return ResultVo.e(400 , "旧密码不正确") ;
		}
		user.setPassword(MD5Utils.encode(newPassword));
		this.userRepository.save(user) ;
		return ResultVo.s();
	}


	public ResultVo saveUser(User user) {
		User tmp = this.userRepository.findByUsername(user.getUsername()) ;
		if(tmp != null){
			return ResultVo.e(400 , "用户名已经存在") ;
		}
		
		user.setPassword(MD5Utils.encode(user.getPassword()));
		this.userRepository.save(user) ;
		
		List<String> roleIdList = user.getRoleIdList() ;
		for(String roleId : roleIdList){
			UserRole userRole = new UserRole() ;
			userRole.setRoleId(roleId);
			userRole.setUserId(user.getId());
			this.userRoleRepository.save(userRole) ;
		}
		
		return ResultVo.s() ;
	}


	public ResultVo modifyUser(User user) {
		User tmp = this.userRepository.findByUsername(user.getUsername()) ;
		if(tmp != null && !user.getId().equals(tmp.getId())){
			return ResultVo.e(400 , "用户名已经存在") ;
		}
		
		this.userRepository.save(user) ;
		List<String> roleIdList = user.getRoleIdList() ;
		List<UserRole> userRoles = this.userRoleRepository.findByUserId(user.getId()); 
		for(UserRole userRole : userRoles){
			if(!roleIdList.remove(userRole.getId())){
				this.userRoleRepository.delete(userRole);
			}
		}
		
		for(String roleId : roleIdList){
			UserRole userRole = new UserRole() ;
			userRole.setRoleId(roleId);
			userRole.setUserId(user.getId());
			this.userRoleRepository.save(userRole) ;
		}
		
		return ResultVo.s() ;
	}
	
	public ResultVo removeUser(String[] ids){
		for(String id : ids){
			User user = this.userRepository.findById(id).get() ;
			if(user.getUsername().equals(Constants.DEFAULT_ADMIN_NAME)){
				return ResultVo.e(400 , "超级管理员不能删除") ;
			}
			
			this.userRepository.deleteById(id);
			this.userRoleRepository.deleteByUserId(id) ; //将角色关系也删除
		}
		
		return ResultVo.s() ;
	}


	public ResultVo disableUser(String[] ids) {
		for(String id : ids){
			User user = this.userRepository.findById(id).get() ;
			if(user.getUsername().equals(Constants.DEFAULT_ADMIN_NAME)){
				return ResultVo.e(400 , "超级管理员不能禁用") ;
			}
			user.setStatus(0);
			this.userRepository.save(user) ;
		}
		
		return ResultVo.s() ;
	}


	public ResultVo enableUser(String[] ids) {
		for(String id : ids){
			User user = this.userRepository.findById(id).get() ;
			user.setStatus(1);
			this.userRepository.save(user) ;
		}
		
		return ResultVo.s() ;
	}


	public ResultVo resetUser(User user) {
		User tmp = this.userRepository.findById(user.getId()).get() ;
		tmp.setPassword(MD5Utils.encode(user.getPassword()));
		this.userRepository.save(tmp) ;
		
		return ResultVo.s() ;
	}
}
