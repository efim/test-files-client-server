package com.excercise.filemanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

@Component
class SimpleNameRepository implements NameRepository {

	private final Lock lock = new ReentrantLock();
	private String saveFileName;
	private Map<String, String> nameToIdMap;
	private PredicateFactory<String> nameSearchPredicateFactory;

	@Autowired
	@Override
	public void setSaveFileName(@Value("${NameRepository.saveFileName}") String saveFileName) {
		this.saveFileName = saveFileName;
	}

	@Autowired
	public SimpleNameRepository(PredicateFactory<String> nameSearchPredicateFactory) {
		this.nameToIdMap = new ConcurrentHashMap<String, String>();
		this.nameSearchPredicateFactory = nameSearchPredicateFactory;

	}

	public SimpleNameRepository(Map<String, String> nameToIdMap,
			PredicateFactory<String> nameSearchPredicateFactory) {
		this.nameToIdMap = nameToIdMap;
		this.nameSearchPredicateFactory = nameSearchPredicateFactory;
	}

	@Override
	public Set<String> getNamesById(String fileId) {
		return nameToIdMap.keySet().stream().filter(key -> nameToIdMap.get(key).equals(fileId))
				.collect(Collectors.toSet());
	}

	@Override
	public String getIdByName(String fileName) {
		return nameToIdMap.get(fileName);
	}

	@Override
	public boolean containsName(String fileName) {
		return nameToIdMap.containsKey(fileName);
	}

	@Override
	public boolean containsId(String fileId) {
		return nameToIdMap.containsValue(fileId);
	}

	@Override
	public void add(String fileName, String fileId) {
		nameToIdMap.put(fileName, fileId);
	}

	@Override
	public void remove(String fileId) {
		while (nameToIdMap.values().remove(fileId))
			;
	}

	@Override
	public Map<String, String> find(String namePart) {
		Map<String, String> result = Maps.filterKeys(nameToIdMap, nameSearchPredicateFactory.getPredicate(namePart));
		return result;
	}

	@Override
	public void saveToDisk() {
		if (lock.tryLock()) {
			// Got the lock
			try (FileOutputStream out = new FileOutputStream(saveFileName)) {
				Properties properties = new Properties();
				properties.putAll(nameToIdMap);
				properties.store(out, null);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// Make sure to unlock so that we don't cause a deadlock
				lock.unlock();
			}
		} else {
			// Someone else had log, save some other time
			return;
		}

	}

	@Override
	public void loadFromDisk() {
		if (lock.tryLock()) {
			try (FileInputStream in = new FileInputStream(saveFileName)) {
				Properties properties = new Properties();
				properties.load(in);
				for (String key : properties.stringPropertyNames()) {
					nameToIdMap.put(key, properties.get(key).toString());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// Make sure to unlock so that we don't cause a deadlock
				lock.unlock();
			}
		} else {
			return;
		}
	}
}
