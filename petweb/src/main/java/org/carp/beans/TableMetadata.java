/**
 * Copyright 2009-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.carp.beans;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author zhou
 * @since 0.1
 */
public class TableMetadata implements Cloneable{
	private Class<?> cls;
	private String table;
	private String schema;
	private String remark;
	private List<PrimarysMetadata> primaryList;
	private List<ColumnsMetadata> columnList;
	private List<DICMetadata> dicList;
	private List<MTOMetadata> mtoList;
	private List<OTMMetadata> otmList;
	private List<OTOMetadata> otoList;
	private List<MappingMetadata> mapList;
	
	public Class<?> getCls() {
		return cls;
	}
	public void setCls(Class<?> cls) {
		this.cls = cls;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<PrimarysMetadata> getPrimaryList() {
		return primaryList;
	}
	
	public void addPrimarysMetadata(PrimarysMetadata primary){
		if(this.primaryList == null)
			this.primaryList = new ArrayList<PrimarysMetadata>(1);
		this.primaryList.add(primary);
	}
	
	public List<ColumnsMetadata> getColumnList() {
		return columnList;
	}

	public void addColumnsMetadata(ColumnsMetadata column){
		if(this.columnList == null)
			this.columnList = new ArrayList<ColumnsMetadata>(10);
		this.columnList.add(column);
	}
	
	public List<DICMetadata> getDicList() {
		return dicList;
	}
	public void addDICMetadata(DICMetadata dic){
		if(this.dicList == null)
			this.dicList = new ArrayList<DICMetadata>(1);
		this.dicList.add(dic);
	}
	
	public List<MTOMetadata> getMtoList() {
		return mtoList;
	}
	public void addMTOMetadata(MTOMetadata mto){
		if(this.mtoList == null)
			this.mtoList = new ArrayList<MTOMetadata>(2);
		this.mtoList.add(mto);
	}
	
	public List<OTMMetadata> getOtmList() {
		return otmList;
	}
	public void addOTMMetadata(OTMMetadata otm){
		if(this.otmList == null)
			this.otmList = new ArrayList<OTMMetadata>(2);
		this.otmList.add(otm);
	}
	
	public List<OTOMetadata> getOtoList() {
		return otoList;
	}
	public void addOTOMetadata(OTOMetadata oto){
		if(this.otoList == null)
			this.otoList = new ArrayList<OTOMetadata>(2);
		this.otoList.add(oto);
	}
	public List<MappingMetadata> getMapList() {
		return mapList;
	}
	public void addMappingMetadata(MappingMetadata mm){
		if(this.mapList == null)
			this.mapList = new ArrayList<MappingMetadata>(2);
		this.mapList.add(mm);
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	@Override
	protected TableMetadata clone() throws CloneNotSupportedException {
		TableMetadata meta = (TableMetadata)super.clone();
		List<ColumnsMetadata> cols = meta.getColumnList();
		if(cols != null){
			List<ColumnsMetadata> colsmd = new ArrayList<ColumnsMetadata>();
			for(ColumnsMetadata col : cols)
				colsmd.add(col.clone());
			meta.setColumnList(colsmd);
		}
		List<DICMetadata> dics = meta.getDicList();
		if(dics != null){
			List<DICMetadata> dicsmd = new ArrayList<DICMetadata>();
			for(DICMetadata dic : dics)
				dicsmd.add(dic.clone());
			meta.setDicList(dicsmd);
		}
		List<MTOMetadata> mtos = meta.getMtoList();
		if(mtos != null){
			List<MTOMetadata> mtosmd = new ArrayList<MTOMetadata>();
			for(MTOMetadata mto : mtos)
				mtosmd.add(mto.clone());
			meta.setMtoList(mtosmd);
		}
		List<OTMMetadata> otms = meta.getOtmList();
		if(otms != null){
			List<OTMMetadata> otmsmd = new ArrayList<OTMMetadata>();
			for(OTMMetadata otm : otms)
				otmsmd.add(otm.clone());
			meta.setOtmList(otmsmd);
		}
		List<OTOMetadata> otos = meta.getOtoList();
		if(otos != null){
			List<OTOMetadata> otosmd = new ArrayList<OTOMetadata>(1);
			for(OTOMetadata oto : otos)
				otosmd.add(oto.clone());
			meta.setOtoList(otosmd);
		}
		List<MappingMetadata> maps = meta.getMapList();
		if(maps != null){
			List<MappingMetadata> mm = new ArrayList<MappingMetadata>(1);
			for(MappingMetadata mmd : maps)
				mm.add(mmd.clone());
			meta.setMapList(mm);
		}
		List<PrimarysMetadata> pms = meta.getPrimaryList();
		if(pms != null){
			List<PrimarysMetadata> pmsmd = new ArrayList<PrimarysMetadata>(1);
			for(PrimarysMetadata pm : pms)
				pmsmd.add(pm.clone());
			meta.setPrimaryList(pmsmd);
		}
		return meta;
	}
	
	public void setPrimaryList(List<PrimarysMetadata> primaryList) {
		this.primaryList = primaryList;
	}
	public void setColumnList(List<ColumnsMetadata> columnList) {
		this.columnList = columnList;
	}
	public void setDicList(List<DICMetadata> dicList) {
		this.dicList = dicList;
	}
	public void setMtoList(List<MTOMetadata> mtoList) {
		this.mtoList = mtoList;
	}
	public void setOtmList(List<OTMMetadata> otmList) {
		this.otmList = otmList;
	}
	public void setOtoList(List<OTOMetadata> otoList) {
		this.otoList = otoList;
	}
	public void setMapList(List<MappingMetadata> mapList) {
		this.mapList = mapList;
	}
}
