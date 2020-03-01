package com.star.shop.basic.service;

import com.star.shop.basic.entity.Permission;
import com.star.shop.basic.entity.Role;
import com.star.shop.basic.repository.BaseRepository;
import com.star.shop.basic.repository.PermissionRepository;
import com.star.shop.basic.repository.RoleRepository;
import com.star.shop.basic.repository.UserRoleRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * <p>
 * Title:RoleService
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
 * @date 2018年8月21日
 */
@Service
public class RoleService extends BaseService<Role, String> {
	private @Resource
	RoleRepository roleRepository;
	
	private @Resource
	PermissionRepository permissionRepository ;
	
	private @Resource
	UserRoleRepository userRoleRepository ;

	@Override
	protected BaseRepository<Role, String> getBaseRepository() {
		return this.roleRepository;
	}
	
	public ResultVo listRole(Map<String, Object> params){
		int pageSize = (int)params.get("pageSize") ;
		int pageNumber = (int)params.get("pageNumber") ;
		
		PageRequest pageable = new PageRequest(pageNumber-1 ,pageSize, Direction.DESC, "ctime") ;
		
		Specification<Role> spec = new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root,CriteriaQuery <?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<>();
				if(!StringUtils.isEmpty(params.get("roleName"))){
					predicate.add(cb.like(root.get("roleName"), "%"+params.get("roleName")+"%")) ;
				}
				
                Predicate[] pre = new Predicate[predicate.size()];
                query = query.where(predicate.toArray(pre)) ;
                return query.getRestriction() ;
			}
		};
		
		Page<Role> page = this.roleRepository.findAll(spec, pageable) ;
		
		ResultVo resultVo = ResultVo.s(page.getContent()) ;
		resultVo.setTotal(page.getTotalElements());
		return resultVo ;
	}
	
	public ResultVo infoRole(String id){
		Role role = this.roleRepository.findById(id).get() ;
		if(role == null){
			return ResultVo.e(400 , "没有查询到数据") ;
		}
		
		List<String> menuIds = this.permissionRepository.findMenuIdByRoleId(id) ;
		role.setMenuIdList(menuIds);
		
		return ResultVo.s(role) ;
	}

	public ResultVo saveRole(Role role) {
		Role tmp = this.roleRepository.findByRoleName(role.getRoleName()) ;
		if(tmp != null){
			return ResultVo.e(400 , "角色名称已经存在") ;
		}
		
		tmp = this.roleRepository.findByRoleSign(role.getRoleSign()) ;
		if(tmp != null){
			return ResultVo.e(400 , "角色标记已经存在") ;
		}
		
		this.roleRepository.save(role) ;
		return ResultVo.s() ;
	}

	public ResultVo modifyRole(Role role) {
		Role tmp = this.roleRepository.findByRoleName(role.getRoleName()) ;
		if(tmp != null && !role.getId().equals(tmp.getId())){
			return ResultVo.e(400 , "角色名称已经存在") ;
		}
		
		tmp = this.roleRepository.findByRoleSign(role.getRoleSign()) ;
		if(tmp != null && !role.getId().equals(tmp.getId())){
			return ResultVo.e(400 , "角色标记已经存在") ;
		}
		this.roleRepository.save(role) ;
		return ResultVo.s() ;
	}

	public ResultVo removeRole(String[] ids) {
		for(String id : ids){
			this.roleRepository.deleteById(id);
			this.permissionRepository.deleteByRoleId(id) ;  //删除授权数据
			this.userRoleRepository.deleteByRoleId(id) ;  //删除角色用户数据
		}
		return ResultVo.s() ;
	}

	public ResultVo authorize(Role role) {
		List<Permission> roleList = this.permissionRepository.findByRoleId(role.getId()) ;
		List<String> menuIdList = role.getMenuIdList() ;
		for(Permission permission : roleList){
			if(!menuIdList.remove(permission.getMenuId())){
				this.permissionRepository.delete(permission);
			}
		}
		for(String menuId : menuIdList){
			Permission permission = new Permission() ;
			permission.setMenuId(menuId);
			permission.setRoleId(role.getId());
			this.permissionRepository.save(permission) ;
		}
		return ResultVo.s() ;
	}

	public ResultVo selectRole() {
		return ResultVo.s(this.roleRepository.findAll());
	}

}
