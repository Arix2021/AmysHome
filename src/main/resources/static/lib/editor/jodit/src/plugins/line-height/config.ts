/*!
 * Jodit Editor (https://xdsoft.net/jodit/)
 * Released under MIT see LICENSE.txt in the project root for license information.
 * Copyright (c) 2013-2022 Valeriy Chupurnov. All rights reserved. https://xdsoft.net
 */

/**
 * @module plugins/line-height
 */

import type { IControlType, IJodit } from 'jodit/types';
import { Config } from 'jodit/config';
import { memorizeExec } from 'jodit/core/helpers';

declare module 'jodit/config' {
	interface Config {
		/**
		 * Default line spacing for the entire editor
		 *
		 * ```js
		 * Jodit.make('#editor', {
		 *   defaultLineHeight: 1.2
		 * })
		 * ```
		 */
		defaultLineHeight: number | null;
	}
}

Config.prototype.defaultLineHeight = null;

Config.prototype.controls.lineHeight = {
	icon: 'line-height',
	command: 'applyLineHeight',
	tags: ['ol'],
	tooltip: 'Line height',
	list: [1, 1.1, 1.2, 1.3, 1.4, 1.5, 2],
	exec: (editor, event, { control }): void | false =>
		memorizeExec(editor, event, { control }, (value: string) => value)
} as IControlType<IJodit> as IControlType;
