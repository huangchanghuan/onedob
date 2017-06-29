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
package org.carp.type;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Ref;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.carp.assemble.ArrayAssemble;
import org.carp.assemble.BigDecimalAssemble;
import org.carp.assemble.BlobAssemble;
import org.carp.assemble.BooleanAssemble;
import org.carp.assemble.ByteAssemble;
import org.carp.assemble.BytesAssemble;
import org.carp.assemble.ClobAssemble;
import org.carp.assemble.DateAssemble;
import org.carp.assemble.DoubleAssemble;
import org.carp.assemble.FloatAssemble;
import org.carp.assemble.InputStreamAssemble;
import org.carp.assemble.IntegerAssemble;
import org.carp.assemble.LongAssemble;
import org.carp.assemble.ObjectAssemble;
import org.carp.assemble.ReaderAssemble;
import org.carp.assemble.RefAssemble;
import org.carp.assemble.SQLDateAssemble;
import org.carp.assemble.ShortAssemble;
import org.carp.assemble.StringAssemble;
import org.carp.assemble.StructAssemble;
import org.carp.assemble.TimeAssemble;
import org.carp.assemble.TimestampAssemble;
import org.carp.assemble.URLAssemble;
import org.carp.engine.parameter.ArrayMapParameter;
import org.carp.engine.parameter.ArrayParameter;
import org.carp.engine.parameter.BlobMapParameter;
import org.carp.engine.parameter.BlobParameter;
import org.carp.engine.parameter.BooleanMapParameter;
import org.carp.engine.parameter.BooleanParameter;
import org.carp.engine.parameter.ByteMapParameter;
import org.carp.engine.parameter.ByteParameter;
import org.carp.engine.parameter.BytesMapParameter;
import org.carp.engine.parameter.BytesParameter;
import org.carp.engine.parameter.ClobMapParameter;
import org.carp.engine.parameter.ClobParameter;
import org.carp.engine.parameter.DateMapParameter;
import org.carp.engine.parameter.DateParameter;
import org.carp.engine.parameter.DecimalMapParameter;
import org.carp.engine.parameter.DecimalParameter;
import org.carp.engine.parameter.DoubleMapParameter;
import org.carp.engine.parameter.DoubleParameter;
import org.carp.engine.parameter.FloatMapParameter;
import org.carp.engine.parameter.FloatParameter;
import org.carp.engine.parameter.InputStreamParameter;
import org.carp.engine.parameter.IntegerMapParameter;
import org.carp.engine.parameter.IntegerParameter;
import org.carp.engine.parameter.LongMapParameter;
import org.carp.engine.parameter.LongParameter;
import org.carp.engine.parameter.ObjectMapParameter;
import org.carp.engine.parameter.ObjectParameter;
import org.carp.engine.parameter.ReaderParameter;
import org.carp.engine.parameter.RefMapParameter;
import org.carp.engine.parameter.RefParameter;
import org.carp.engine.parameter.ShortMapParameter;
import org.carp.engine.parameter.ShortParameter;
import org.carp.engine.parameter.SqlDateParameter;
import org.carp.engine.parameter.StringMapParameter;
import org.carp.engine.parameter.StringParameter;
import org.carp.engine.parameter.StructParameter;
import org.carp.engine.parameter.TimeMapParameter;
import org.carp.engine.parameter.TimeParameter;
import org.carp.engine.parameter.TimestampMapParameter;
import org.carp.engine.parameter.TimestampParameter;

public class TypesMapping {
	private final static Map<Class<?>,Class<?>> fieldMap = new HashMap<Class<?>,Class<?>>(30); 
	private final static Map<Class<?>,Class<?>> javaTypeMap = new HashMap<Class<?>,Class<?>>(30); 
	private final static Map<Integer,Class<?>> sqlTypeMap = new HashMap<Integer,Class<?>>(30);
	private final static Map<Integer,Class<?>> sqlJavaMap = new HashMap<Integer,Class<?>>();
	static{
		//field”≥…‰
		fieldMap.put(int.class, IntegerAssemble.class);
		fieldMap.put(Integer.class, IntegerAssemble.class);
		fieldMap.put(byte.class, ByteAssemble.class);
		fieldMap.put(Byte.class, ByteAssemble.class);
		fieldMap.put(byte[].class, BytesAssemble.class);
		fieldMap.put(short.class, ShortAssemble.class);
		fieldMap.put(Short.class, ShortAssemble.class);
		fieldMap.put(long.class, LongAssemble.class);
		fieldMap.put(Long.class, LongAssemble.class);
		fieldMap.put(Blob.class, BlobAssemble.class);
		fieldMap.put(Clob.class, ClobAssemble.class);
		fieldMap.put(String.class, StringAssemble.class);
		fieldMap.put(java.sql.Date.class, SQLDateAssemble.class);
		fieldMap.put(Date.class, DateAssemble.class);
		fieldMap.put(Time.class, TimeAssemble.class);
		fieldMap.put(Timestamp.class, TimestampAssemble.class);
		fieldMap.put(float.class, FloatAssemble.class);
		fieldMap.put(Float.class, FloatAssemble.class);
		fieldMap.put(Double.class, DoubleAssemble.class);
		fieldMap.put(double.class, DoubleAssemble.class);
		fieldMap.put(BigDecimal.class, BigDecimalAssemble.class);
		fieldMap.put(boolean.class, BooleanAssemble.class);
		fieldMap.put(Boolean.class, BooleanAssemble.class);
		fieldMap.put(Object.class, ObjectAssemble.class);
		fieldMap.put(InputStream.class, InputStreamAssemble.class);
		fieldMap.put(Reader.class, ReaderAssemble.class);
		fieldMap.put(Ref.class, RefAssemble.class);
		fieldMap.put(Array.class, ArrayAssemble.class);
		fieldMap.put(java.sql.Struct.class, StructAssemble.class);
		fieldMap.put(java.net.URL.class, URLAssemble.class);
		
		//java class to PreparedStatement Parameter mapping
		javaTypeMap.put(int.class, IntegerParameter.class);
		javaTypeMap.put(Integer.class, IntegerParameter.class);
		javaTypeMap.put(byte.class, ByteParameter.class);
		javaTypeMap.put(Byte.class, ByteParameter.class);
		javaTypeMap.put(byte[].class, BytesParameter.class);
		javaTypeMap.put(short.class, ShortParameter.class);
		javaTypeMap.put(Short.class, ShortParameter.class);
		javaTypeMap.put(long.class, LongParameter.class);
		javaTypeMap.put(Long.class, LongParameter.class);
		javaTypeMap.put(float.class, FloatParameter.class);
		javaTypeMap.put(Float.class, FloatParameter.class);
		javaTypeMap.put(Double.class, DoubleParameter.class);
		javaTypeMap.put(double.class, DoubleParameter.class);
		javaTypeMap.put(BigDecimal.class, DecimalParameter.class);
		javaTypeMap.put(boolean.class, BooleanParameter.class);
		javaTypeMap.put(Boolean.class, BooleanParameter.class);
		javaTypeMap.put(Blob.class, BlobParameter.class);
		javaTypeMap.put(Clob.class, ClobParameter.class);
		javaTypeMap.put(String.class, StringParameter.class);
		javaTypeMap.put(Date.class, DateParameter.class);
		javaTypeMap.put(java.sql.Date.class, SqlDateParameter.class);
		javaTypeMap.put(Time.class, TimeParameter.class);
		javaTypeMap.put(Timestamp.class, TimestampParameter.class);
		javaTypeMap.put(Object.class, ObjectParameter.class);
		javaTypeMap.put(InputStream.class, InputStreamParameter.class);
		javaTypeMap.put(FileInputStream.class, InputStreamParameter.class);
		javaTypeMap.put(Reader.class, ReaderParameter.class);
		javaTypeMap.put(FileReader.class, ReaderParameter.class);
		javaTypeMap.put(Ref.class, RefParameter.class);
		javaTypeMap.put(Array.class, ArrayParameter.class);
		javaTypeMap.put(java.sql.Struct.class, StructParameter.class);
		
		//sql types to PreparedStatement Parameter mapping
		sqlTypeMap.put(Types.ARRAY, ArrayMapParameter.class);
		sqlTypeMap.put(Types.BIGINT, LongMapParameter.class);
		sqlTypeMap.put(Types.BINARY, BytesMapParameter.class);
		sqlTypeMap.put(Types.BLOB, BlobMapParameter.class);
		sqlTypeMap.put(Types.BOOLEAN, BooleanMapParameter.class);
		sqlTypeMap.put(Types.CHAR, StringMapParameter.class);
		sqlTypeMap.put(Types.CLOB, ClobMapParameter.class);
		sqlTypeMap.put(Types.DATE, DateMapParameter.class);
		sqlTypeMap.put(Types.DECIMAL, DecimalMapParameter.class);
		sqlTypeMap.put(Types.DOUBLE, DoubleMapParameter.class);
		sqlTypeMap.put(Types.FLOAT, FloatMapParameter.class);
		sqlTypeMap.put(Types.INTEGER, IntegerMapParameter.class);
		sqlTypeMap.put(Types.JAVA_OBJECT, ObjectMapParameter.class);
		sqlTypeMap.put(Types.LONGVARBINARY, BytesMapParameter.class);
		sqlTypeMap.put(Types.LONGVARCHAR, BytesMapParameter.class);
		sqlTypeMap.put(Types.NUMERIC, DecimalMapParameter.class);
		sqlTypeMap.put(Types.REAL, DoubleMapParameter.class);
		sqlTypeMap.put(Types.REF, RefMapParameter.class);
		sqlTypeMap.put(Types.SMALLINT, ShortMapParameter.class);
		sqlTypeMap.put(Types.TIME, TimeMapParameter.class);
		sqlTypeMap.put(Types.TIMESTAMP, TimestampMapParameter.class);
		sqlTypeMap.put(Types.TINYINT, ByteMapParameter.class);
		sqlTypeMap.put(Types.VARBINARY, BytesMapParameter.class);
		sqlTypeMap.put(Types.VARCHAR, StringMapParameter.class);
		
		//sql types to java class mapping
		sqlJavaMap.put(Types.BIT, Byte.class);
		sqlJavaMap.put(Types.BIGINT, Long.class);
		sqlJavaMap.put(Types.BINARY, InputStream.class);
		sqlJavaMap.put(Types.BLOB, InputStream.class);
		sqlJavaMap.put(Types.BOOLEAN, Boolean.class);
		sqlJavaMap.put(Types.CHAR, String.class);
		sqlJavaMap.put(Types.CLOB, Reader.class);
		sqlJavaMap.put(Types.DATE, Date.class);
		sqlJavaMap.put(Types.DECIMAL, BigDecimal.class);
		sqlJavaMap.put(Types.DOUBLE, Double.class);
		sqlJavaMap.put(Types.FLOAT, Float.class);
		sqlJavaMap.put(Types.INTEGER, Integer.class);
		sqlJavaMap.put(Types.JAVA_OBJECT, Object.class);
		sqlJavaMap.put(Types.LONGVARBINARY, InputStream.class);
		sqlJavaMap.put(Types.LONGVARCHAR, InputStream.class);
		sqlJavaMap.put(Types.NUMERIC, BigDecimal.class);
		sqlJavaMap.put(Types.OTHER, Object.class);
		sqlJavaMap.put(Types.REAL, Double.class);
		sqlJavaMap.put(Types.REF, Ref.class);
		sqlJavaMap.put(Types.SMALLINT, Short.class);
		sqlJavaMap.put(Types.STRUCT, Enum.class);
		sqlJavaMap.put(Types.TIME, Time.class);
		sqlJavaMap.put(Types.TIMESTAMP, Timestamp.class);
		sqlJavaMap.put(Types.TINYINT, Byte.class);
		sqlJavaMap.put(Types.VARBINARY, InputStream.class);
		sqlJavaMap.put(Types.VARCHAR, String.class);
	}
	
	public static Class<?> getAssembleClass(Class<?> cls){
		return fieldMap.get(cls);
	}
	
	public static Class<?> getParamsClass(Class<?> cls){
		return javaTypeMap.get(cls);
	}
	
	public static Class<?> getParamsClass(Integer i){
		return sqlTypeMap.get(i);
	}
	
	public static Class<?> getJavaType(int sqlType){
		return sqlJavaMap.get(sqlType);
	}
	
}
