/*!
 * Jodit Editor (https://xdsoft.net/jodit/)
 * Released under MIT see LICENSE.txt in the project root for license information.
 * Copyright (c) 2013-2022 Valeriy Chupurnov. All rights reserved. https://xdsoft.net
 */

/**
 * @module event-emitter
 */

import type { CanUndef } from 'jodit/types';

/**
 * Class for adding event handling capability
 *
 * ```ts
 * class SomeClass extends Eventify<{ start: (node: Node) => boolean; }> {
 * 	constructor() {
 * 		super();
 * 		setTimeout(() => {
 * 			if (this.emit('start', document.body)) {
 * 				console.log('yes');
 * 			};
 * 		}, 100);
 * 	}
 * }
 *
 * const sm = new SomeClass();
 * sm.on('start', (node) => {
 * 	console.log(node);
 * 	return true;
 * })
 * ```
 */
export abstract class Eventify<
	MAP extends { [key: string]: (...args: any[]) => any },
	EVENT extends keyof MAP = keyof MAP
> {
	private map: Map<keyof MAP, Set<Function>> = new Map();

	on(name: EVENT, func: MAP[EVENT]): this {
		if (!this.map.has(name)) {
			this.map.set(name, new Set());
		}

		this.map.get(name)?.add(func);

		return this;
	}

	off(name: keyof MAP, func: MAP[EVENT]): this {
		if (this.map.has(name)) {
			this.map.get(name)?.delete(func);
		}

		return this;
	}

	protected emit(
		name: EVENT,
		...args: Parameters<MAP[EVENT]>
	): CanUndef<ReturnType<MAP[EVENT]>> {
		let result: CanUndef<ReturnType<MAP[EVENT]>>;

		if (this.map.has(name)) {
			this.map.get(name)?.forEach(cb => {
				result = cb(...args);
			});
		}

		return result;
	}
}
