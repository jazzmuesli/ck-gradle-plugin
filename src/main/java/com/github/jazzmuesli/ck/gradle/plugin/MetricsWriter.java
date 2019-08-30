package com.github.jazzmuesli.ck.gradle.plugin;

import com.github.mauricioaniche.ck.CKNotifier;

/**
 * TODO: inherit it from ck-mvn-plugin
 */
public interface MetricsWriter extends CKNotifier {
	void finish();
}