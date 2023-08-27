/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.youxel.core.base.activity


/**
 * To be implemented by components that host top-level navigation destinations.
 */
interface ToolbarListener {
    fun setActivityToolbarTitle(title: String, gravity: Int? = null)

    /**
     * Called by BaseFragment to show fragment toolbar and hide the activity toolbar
     * */
    fun hideActivityToolbar()

    /**
     * Called show the activity toolbar
     */
    fun showActivityToolbar(isShowBack: Boolean? = false)

    fun toggleSearchIconToolbar(isShow: Boolean? = true)

    fun loadUserProfileImage(userProfileImageUrl: String)
}

